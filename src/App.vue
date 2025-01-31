<template>
  <div id="app">
    <BasicLayout />
  </div>
</template>

<style>
#app {
}
</style>

<script setup lang="ts">
import BasicLayout from "@/layouts/BasicLayout";
import { onMounted } from "vue";

/**
 * global initialization function:
 * Only run once,
 * all code that will be called globally for once can be putted here.
 */
const doInit = () => {
  // todo: to add some initialization code here
  console.log("hello, welcome to OJ system!");
};

onMounted(() => {
  doInit();
});
</script>
