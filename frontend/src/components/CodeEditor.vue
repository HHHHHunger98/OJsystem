<template>
  <div id="code-editor" ref="codeEditorRef" style="min-height: 400px"></div>
  <a-button @click="updateValue">update</a-button>
</template>
<script setup lang="ts">
import * as monaco from "monaco-editor";
import { onMounted, withDefaults, defineProps, ref, toRaw } from "vue";

const codeEditorRef = ref();
const codeEditor = ref();

/**
 * Define the component attributes
 */
interface Props {
  value: string;
  handleChange: (v: string) => void;
}
const props = withDefaults(defineProps<Props>(), {
  value: () => "",
  handleChange: (v: string) => {
    console.log(v);
  },
});

const updateValue = () => {
  if (!codeEditor.value) {
    return;
  }
  toRaw(codeEditor.value).setValue("new");
};
onMounted(() => {
  if (!codeEditorRef.value) {
    return;
  }
  codeEditor.value = monaco.editor.create(codeEditorRef.value, {
    value: props.value,
    language: "java",
    automaticLayout: true,
    // lineNumbers: "on",
    // roundedSelection: false,
    // scrollBeyondLastLine: false,
    // readOnly: false,
    theme: "vs-dark",
    minimap: {
      enabled: true,
    },
  });

  codeEditor.value.onDidChangeModelContent(() => {
    props.handleChange(toRaw(codeEditor.value).getValue());
  });
});
</script>
<style></style>
