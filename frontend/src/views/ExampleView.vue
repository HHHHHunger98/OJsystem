<template>
  <div id="exampleView">
    {{ mdValue }}
    {{ codeValue }}
    <MdEditor :value="mdValue" :handle-change="onMdChange" />
    <CodeEditor :value="codeValue" :handle-change="onCodeChange" />
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import MdEditor from "@/components/MdEditor.vue";
import CodeEditor from "@/components/CodeEditor.vue";

const codeValue = ref();
const mdValue = ref();

const onCodeChange = (v: string) => {
  codeValue.value = v;
};
const onMdChange = (v: string) => {
  mdValue.value = v;
};
</script>
