package com.amadeus.service;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import com.amadeus.dto.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j

public class ChatService {

    @Autowired
    private ChatClient chatClient;

    private static final String TEMPLATE =
            "文件名:{fileName}" +
                    "文件内容:{preview}" +
                    "用户的问题:{userPrompt}" +
                    "请严格使用中文回答，回答内容需要与文件内容相关，可以适当扩展";



    public ChatResponse process(MultipartFile file, @NotBlank(message = "prompt不能为空") String prompt) throws IOException {
        try{
            //1.读取excel转为String字符串
            String preview = readExcel(file);

            //2.拼装内容发送给大模型
            String request = new PromptTemplate(TEMPLATE).render(
                    Map.of(
                            "fileName", file.getOriginalFilename(),
                            "preview", preview,
                            "userPrompt", prompt
                    )
            );

            //3.调用大模型,发送请求
            String aiAnswer = chatClient.prompt()
                    .user(request)
                    .call()
                    .content();

            log.info("大模型的返回结果如下{}",aiAnswer);
            return new ChatResponse(
                    file.getOriginalFilename(),
                    preview,
                    aiAnswer
            );
        }catch (IOException e){
            throw new RuntimeException(e);
        }


    }
    public String readExcel(MultipartFile file) throws IOException {
        // 1. 准备字符串容器
        StringBuilder stringBuilder = new StringBuilder("原始的excel数据\n");
        // 2. 把文件转换成字节数组
        byte[] fileBytes = file.getBytes();
        // 3. 调用ExcelReader扫描sheet信息
        ExcelReader excelReader = EasyExcel.read(new ByteArrayInputStream(fileBytes)).build();
        List<ReadSheet> readSheets = excelReader.excelExecutor().sheetList();
        // 4. 逐一sheet去读取excel信息
        for (ReadSheet readSheet : readSheets) {
            int sheetNo = readSheet.getSheetNo();
            List<List<String>> sheetRows = new ArrayList<>();
            EasyExcel.read(new ByteArrayInputStream(fileBytes), new ReadListener<Map<Integer, String>>() {
                // 5. 把读取到的内容逐一添加到字符串容器
                @Override
                public void invoke(Map<Integer, String> data, AnalysisContext analysisContext) {
                    List<String> rowData = new ArrayList<>();
                    int maxColumnIndex = data.keySet().stream().mapToInt(Integer::intValue).max().orElse(-1);
                    for (int i = 0; i <= maxColumnIndex; i++) {
                        String value = data.getOrDefault(i, "");
                        rowData.add(value.trim());
                    }
                    sheetRows.add(rowData);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }
            }).sheet(sheetNo).headRowNumber(0).doRead();
            stringBuilder.append("\n==sheet: ").append(readSheet.getSheetName())
                    .append("(第").append(sheetNo + 1).append("个工作表)==\n");

            for (List<String> row : sheetRows) {
                for (int i = 0; i < row.size(); i++) {
                    if (i > 0) {
                        stringBuilder.append("|");
                    }
                    stringBuilder.append(row.get(i));
                }
                stringBuilder.append("\n");
            }
        }
        // 6. 返回字符串容器的内容
        return stringBuilder.toString();
    }
}
