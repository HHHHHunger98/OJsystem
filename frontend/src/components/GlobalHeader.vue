<template>
  <a-row id="globalHeader" align="center" :wrap="false">
    <a-col flex="auto">
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
        <a-menu-item v-for="item in visibleRoutes" :key="item.path">
          {{ item.name }}
        </a-menu-item>
      </a-menu>
    </a-col>
    <a-col flex="100px">
      <div>
        {{ store.state.user?.loginUser?.userName ?? "未登錄" }}
      </div>
    </a-col>
  </a-row>
</template>

<script setup lang="ts">
import checkAccess from "@/access/checkAccess";
import { routes } from "@/router/routes";
import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

const router = useRouter();
const store = useStore();

const selectedKey = ref(["/"]);

router.afterEach((to, from, failure) => {
  selectedKey.value = [to.path];
});

const doMenuClick = (key: string) => {
  router.push({
    path: key,
  });
};

const visibleRoutes = computed(() => {
  return routes.filter((item, index) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    if (
      !checkAccess(store.state.user.loginUser, item?.meta?.access as string)
    ) {
      return false;
    }
    return true;
  });
});
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
