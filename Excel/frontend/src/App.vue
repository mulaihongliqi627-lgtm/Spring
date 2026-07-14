<script setup>
import { ref } from 'vue';
import axios from 'axios';

const fileInput = ref(null);
const fileName = ref('');
const prompt = ref('请帮我总结Excel内容，并给出下一步建议。');
const loading = ref(false);
const result = ref(null);
const error = ref('');

const resetResult = () => {
  result.value = null;
  error.value = '';
};

const handleFile = (event) => {
  const [file] = event.target.files;
  if (file) {
    fileName.value = file.name;
    resetResult();
  }
};

const submit = async () => {
  const file = fileInput.value?.files?.[0];
  if (!file) {
    error.value = '请先选择需要上传的文件。';
    return;
  }
  if (!prompt.value.trim()) {
    error.value = '请输入问题或指令。';
    return;
  }

  const formData = new FormData();
  formData.append('file', file);
  formData.append('prompt', prompt.value);

  loading.value = true;
  error.value = '';

  try {
    // 通过本地开发代理转发到后端
    const { data } = await axios.post('/api/chat/process', formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    });
    result.value = data;
  } catch (err) {
    error.value = err?.response?.data?.message || '请求失败，请检查后端服务或网络。';
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <main class="card">
    <header>
      <h1>简易 Chat2Excel</h1>
      <p>上传Excel，结合AI获取总结与建议。</p>
    </header>

    <section class="panel">
      <label class="field block">
        <span>选择文件</span>
        <input ref="fileInput" type="file" accept=".csv,.xlsx,.xls,.txt" @change="handleFile" />
        <small v-if="fileName">已选择：{{ fileName }}</small>
      </label>

      <label class="field block">
        <span>提问 / 指令</span>
        <textarea v-model="prompt" rows="4" placeholder="例如：请输出销售额TOP5的城市，并给出建议。" />
      </label>

      <button class="primary" :disabled="loading" @click="submit">
        {{ loading ? '处理中...' : '上传并生成' }}
      </button>

      <p v-if="error" class="error">{{ error }}</p>
    </section>

    <section v-if="result" class="panel result">
      <h2>AI返回</h2>
      <p class="meta">
        <span>文件：{{ result.fileName }}</span>
        <span>存储位置：{{ result.storedPath }}</span>
      </p>
      <article>
        <h3>AI建议</h3>
        <pre>{{ result.aiAnswer }}</pre>
      </article>
    </section>
  </main>
</template>

<style scoped>
.card {
  background-color: #fff;
  border-radius: 24px;
  padding: 32px;
  box-shadow: 0 24px 60px rgba(15, 23, 42, 0.15);
  display: flex;
  flex-direction: column;
  gap: 24px;
}

header h1 {
  margin: 0;
  font-size: 28px;
  color: #111827;
}

header p {
  margin: 8px 0 0;
  color: #6b7280;
}

.panel {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.field span {
  display: inline-block;
  margin-bottom: 8px;
  font-weight: 600;
}

input[type='file'],
textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #d1d5db;
  border-radius: 12px;
  font-size: 16px;
}

textarea {
  resize: vertical;
}

.primary {
  padding: 14px 18px;
  border: none;
  border-radius: 12px;
  background: linear-gradient(90deg, #6366f1, #8b5cf6);
  color: #fff;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
}

.primary:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.error {
  color: #dc2626;
}

.result pre {
  background: #f9fafb;
  padding: 16px;
  border-radius: 12px;
  white-space: pre-wrap;
  line-height: 1.5;
}

.meta {
  display: flex;
  flex-direction: column;
  gap: 4px;
  color: #4b5563;
  font-size: 14px;
}
</style>

