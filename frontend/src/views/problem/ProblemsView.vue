<template>
  <div id="problemsView" style="align-items: center">
    <h2 style="text-align: center">Problem Management</h2>
    <a-config-provider :locale="enUS">
      <a-form :model="queryCondition" layout="inline">
        <a-form-item style="min-width: 300px" field="title" label="Title">
          <a-input
            v-model="queryCondition.title"
            placeholder="please enter title"
          />
        </a-form-item>
        <a-form-item style="min-width: 300px" field="tags" label="Tags">
          <a-input-tag
            v-model="queryCondition.tags"
            placeholder="please enter your tags"
          />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" html-type="submit" @click="doSearch"
            >Search</a-button
          >
        </a-form-item>
      </a-form>
      <a-table
        :columns="columns"
        :data="dataList"
        :pagination="{
          pageSize: queryCondition.pageSize,
          current: queryCondition.current,
          total,
          showTotal: true,
          showJumper: true,
        }"
        @page-change="onPageChange"
      >
        <template #tags="{ record }">
          <a-space wrap>
            <a-tag
              v-for="(tag, index) of record.tags"
              :key="index"
              color="green"
              >{{ tag }}</a-tag
            >
          </a-space>
        </template>
        <template #acceptedRate="{ record }">
          {{
            `${
              record.submitNum && record.submitNum
                ? record.acceptedNum / record.submitNum
                : "0"
            }% (${record.acceptedNum ? record.acceptedNum : "0"}/${
              record.submitNum
            })`
          }}
        </template>
        <template #createTime="{ record }">
          {{ moment(record.createTime).format("YYYY-MM-DD") }}
        </template>
        <template #optional="{ record }">
          <a-space>
            <a-button type="primary" @click="toProblemPage(record)"
              >View</a-button
            >
          </a-space>
        </template>
      </a-table>
    </a-config-provider>
  </div>
</template>
<script setup lang="ts">
import Message from "@arco-design/web-vue/es/message";
import enUS from "@arco-design/web-vue/es/locale/lang/en-us";
import moment from "moment";
import {
  listProblemVoByPageUsingPost,
  Problem,
} from "../../../generated/index";
import { onMounted, ref, watchEffect } from "vue";
import { useRouter } from "vue-router";

const show = ref(true);

const dataList = ref([]);
const total = ref(0);
const queryCondition = ref({
  title: "",
  tags: [],
  pageSize: 2,
  current: 1,
});
const router = useRouter();

const loadData = async () => {
  const { status, data } = await listProblemVoByPageUsingPost({
    body: queryCondition.value,
  });
  console.log(data);
  if (status === 200) {
    if (data?.code === 0) {
      dataList.value = data.data.records;
      total.value = data.data.total;
    } else {
      Message.error("Failed:" + data?.message);
      if (data?.code === 40100) {
        router.push({
          path: "/user/login",
        });
      }
    }
  } else {
    Message.error("Connecting to server error");
  }
};

onMounted(() => {
  loadData();
});

const columns = [
  {
    title: "Id",
    dataIndex: "id",
  },
  {
    title: "Title",
    dataIndex: "title",
  },
  {
    title: "Tags",
    slotName: "tags",
  },
  {
    title: "Accepted Rate",
    slotName: "acceptedRate",
  },
  {
    title: "Created Date",
    slotName: "createTime",
  },
  {
    title: "Optional",
    slotName: "optional",
  },
];

/**
 * Jumps to the problem viewing page
 * @param problem selected problem item from the list
 */
const toProblemPage = (problem: Problem) => {
  router.push({
    path: `/view/problem/${problem.id}`,
  });
};

const onPageChange = (page: number) => {
  queryCondition.value = {
    ...queryCondition.value,
    current: page,
  };
};

const doSearch = () => {
  queryCondition.value = {
    ...queryCondition.value,
    current: 1,
  };
};

/**
 * listen the change of loadDate, for example:
 * when the variable queryCondition.current change, then do the page reload
 */
watchEffect(() => {
  loadData();
});
</script>
<style lang="css">
#problemsView {
  max-width: 1280px;
  margin: 0 auto;
}
</style>
