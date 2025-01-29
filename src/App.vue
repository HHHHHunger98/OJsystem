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
import { useRouter } from "vue-router";
import { useStore } from "vuex";

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

const router = useRouter();
const store = useStore();

router.beforeEach((to, from, next) => {
  if (to.meta?.access === "canAdmin") {
    if (store.state.user.loginUser?.role !== "admin") {
      next("/NoAuth");
      return;
    }
  }
  next();
});
</script>
