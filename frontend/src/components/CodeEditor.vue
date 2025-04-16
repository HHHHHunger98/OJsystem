<template>
  <div id="code-editor" ref="codeEditorRef" style="min-height: 400px"></div>
</template>
<script setup lang="ts">
import * as monaco from "monaco-editor";
import { onMounted, withDefaults, defineProps, ref, toRaw, watch } from "vue";

const codeEditorRef = ref();
let codeEditor = ref();

/**
 * Define the component attributes
 */
interface Props {
  value: string;
  language?: string;
  handleChange: (v: string) => void;
}
const props = withDefaults(defineProps<Props>(), {
  value: () => "",
  language: () => "cpp",
  handleChange: (v: string) => {
    console.log(v);
  },
});

watch(
  () => props.language,
  () => {
    if (codeEditor.value) {
      monaco.editor.setModelLanguage(
        toRaw(codeEditor.value).getModel(),
        props.language
      );
    }
  }
);

onMounted(() => {
  if (!codeEditorRef.value) {
    return;
  }
  codeEditor.value = monaco.editor.create(codeEditorRef.value, {
    value: props.value,
    language: props.language,
    automaticLayout: true,
    lineNumbers: "on",
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
