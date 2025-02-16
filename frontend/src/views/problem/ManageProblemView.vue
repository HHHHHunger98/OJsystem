<template>
  <div id="manageProblemView" style="align-items: center">
    <h2 style="text-align: center">Problem Management</h2>
    <a-config-provider :locale="enUS">
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
        <template #optional="{ record }">
          <a-space>
            <a-button type="primary" @click="doEdit(record)">Edit</a-button>
            <a-button status="danger" @click="doDelete(record)"
              >Delete</a-button
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
import {
  deleteProblemUsingPost,
  listProblemByPageUsingPost,
  Problem,
} from "../../../generated/index";
import { onMounted, ref, watchEffect } from "vue";
import { useRouter } from "vue-router";

const show = ref(true);
const router = useRouter();

const dataList = ref([]);
const total = ref(0);
const queryCondition = ref({
  pageSize: 10,
  current: 1,
});
const loadData = async () => {
  const { data, status } = await listProblemByPageUsingPost({
    body: queryCondition.value,
  });
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
  console.log(dataList);
});

/**
 * Object { id: "1", title: "A + D", content: "new content: to do ...", … }
 * acceptNum:
 * content:
 * createTime: "2025-02-09T11:34:22.000+00:00"
 * favourNum: 0
 * id: "1"
 * isDelete: 0
 * judgeCase: '[{"input":"new input","output":"new output"}]'
 * judgeConfig: '{"timeLimit":1000,"spaceLimit":1,"stackLimit":100}'
 * solution: "new solution"
 * submitNum: 0
 * tags: '["binary tree"]'
 * thumbNum: 0
 * title: "A + D"
 * updateTime: "2025-02-09T13:14:35.000+00:00"
 * userId: "1885054067028406274"
 */

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
    title: "Content",
    dataIndex: "content",
  },
  {
    title: "Tags",
    dataIndex: "tags",
  },
  {
    title: "Solution",
    dataIndex: "solution",
  },
  {
    title: "Submit Times",
    dataIndex: "submitNum",
  },
  {
    title: "Accepted Times",
    dataIndex: "acceptNum",
  },
  {
    title: "Judge Configuration",
    dataIndex: "judgeConfig",
  },
  {
    title: "Judge Cases",
    dataIndex: "judgeCase",
  },
  {
    title: "User ID",
    dataIndex: "userId",
  },
  {
    title: "Created Date",
    dataIndex: "createTime",
  },
  {
    title: "Optional",
    slotName: "optional",
  },
];

const doDelete = async (record: Problem) => {
  const res = await deleteProblemUsingPost({
    body: {
      id: record.id,
    },
  });
  if (res.status === 200) {
    if (res.data.code === 0) {
      Message.success("Successfully deleted");
      // todo Updata Current Problem List View.
      loadData();
    } else {
      Message.error("Delete Operation Failed:" + res.data.message);
    }
  } else {
    Message.error("Connecting to server error");
  }
};
const doEdit = (record: Problem) => {
  router.push({
    path: "/update/problem",
    query: {
      id: record.id,
    },
  });
};

const onPageChange = (page: number) => {
  queryCondition.value = {
    ...queryCondition.value,
    current: page,
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
<style lang="css"></style>
