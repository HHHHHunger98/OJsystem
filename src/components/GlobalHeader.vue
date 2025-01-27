<template>
  <div id="globalHeader">
    <a-menu
      mode="horizontal"
      :selected-keys="selectedKey"
      @menu-item-click="doMenuClick"
    >
      <a-menu-item
        key="0"
        :style="{ padding: 0, marginRight: '38px' }"
        disabled
      >
        <div class="title-bar">
          <img class="logo" src="../assets/LogoOJ.png" />
          <div class="title">MyOJ</div>
        </div>
      </a-menu-item>
      <a-menu-item v-for="item in routes" :key="item.path">
        {{ item.name }}
      </a-menu-item>
    </a-menu>
  </div>
</template>

<script setup lang="ts">
import { routes } from "@/router/routes";
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const selectedKey = ref(["/"]);

router.afterEach((to, from, failure) => {
  selectedKey.value = [to.path];
});

const doMenuClick = (key: string) => {
  router.push({
    path: key,
  });
};
</script>

<style scoped>
.title-bar {
  display: flex;
  align-items: center;
}
.title {
  color: #444;
  margin-left: 16px;
}
.logo {
  height: 64px;
}
</style>
