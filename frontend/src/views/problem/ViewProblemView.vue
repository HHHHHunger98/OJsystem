<template>
  <div id="viewProblemView" style="align-items: center">
    <h2 style="text-align: center">Problem {{ problem?.id }}</h2>
    <a-config-provider :locale="enUS">
      <a-row :gutter="[24, 24]">
        <a-col :md="12" :xs="24">
          <a-tabs default-active-key="problem">
            <a-tab-pane key="problem" title="Problem">
              <template #title> <icon-list /> Problem </template>
              <a-card v-if="problem" :title="problem.title">
                <a-descriptions
                  title="Judge Configuration"
                  :column="{ xs: 1, md: 2, lg: 3 }"
                >
                  <a-descriptions-item label="Time Limit">
                    <a-tag>{{ problem.judgeConfig?.timeLimit ?? 0 }}</a-tag>
                  </a-descriptions-item>
                  <a-descriptions-item label="Space Limit">
                    <a-tag>{{ problem.judgeConfig?.spaceLimit ?? 0 }}</a-tag>
                  </a-descriptions-item>
                  <a-descriptions-item label="Stack Limit">
                    <a-tag>{{ problem.judgeConfig?.stackLimit ?? 0 }}</a-tag>
                  </a-descriptions-item>
                </a-descriptions>
                <MdViewer :value="problem?.content || ''"></MdViewer>
                <template #extra>
                  <a-space wrap>
                    <a-tag
                      v-for="(tag, index) of problem.tags"
                      :key="index"
                      color="green"
                      >{{ tag }}</a-tag
                    >
                  </a-space>
                </template>
              </a-card>
            </a-tab-pane>
            <a-tab-pane key="solution">
              <template #title> <icon-clock-circle /> Solution </template>
              To do: Solution to the problem {{ problem?.id }}
            </a-tab-pane>
            <a-tab-pane key="comment">
              <template #title> <icon-message /> Comment </template>
              Todo: Comments
            </a-tab-pane>
          </a-tabs>
        </a-col>
        <a-col :md="12" :xs="24">
          <a-form :model="form" :style="{ width: '600px' }">
            <a-form-item
              field="name"
              tooltip="Please select the programming language for the solution"
              label="Language"
            >
              <a-select
                v-model="form.language"
                :style="{ width: '160px' }"
                placeholder="Select the programming language"
                :trigger-props="{ autoFitPopupMinWidth: true }"
              >
                <a-option>java</a-option>
                <a-option>cpp</a-option>
                <a-option>go</a-option>
                <a-option>python</a-option>
              </a-select>
            </a-form-item>
          </a-form>
          <CodeEditor
            :value="form.code as string"
            :language="form.language"
            :handle-change="onCodeChange"
          />
          <a-divider :size="0" />
          <a-button type="primary" style="min-width: 200px" @click="doSubmit"
            >Submit</a-button
          >
        </a-col>
      </a-row>
    </a-config-provider>
  </div>
</template>
<script setup lang="ts">
import Message from "@arco-design/web-vue/es/message";
import enUS from "@arco-design/web-vue/es/locale/lang/en-us";
import {
  doProblemSubmitUsingPost,
  getProblemVoByIdUsingGet,
  ProblemSubmitAddRequest,
  ProblemVo,
} from "../../../generated/index";
import { onMounted, ref, withDefaults, defineProps } from "vue";
import CodeEditor from "@/components/CodeEditor.vue";
import MdViewer from "@/components/MdViewer.vue";
import { useRouter } from "vue-router";

const show = ref(true);

interface Props {
  id: string;
}

const props = withDefaults(defineProps<Props>(), {
  id: () => "",
});

const problem = ref<ProblemVo>();

const loadData = async () => {
  const { status, data } = await getProblemVoByIdUsingGet({
    query: {
      id: props.id as any,
    },
  });
  if (status === 200) {
    if (data?.code === 0) {
      problem.value = data.data;
    } else {
      Message.error("Failed:" + data?.message);
    }
  } else {
    Message.error("Connecting to server error");
  }
};

onMounted(() => {
  loadData();
  console.log(props);
});

const onCodeChange = (v: string) => {
  form.value.code = v;
};

const router = useRouter();

const form = ref<ProblemSubmitAddRequest>({
  language: "java",
  code: "",
});

/**
 * submit the code
 */
const doSubmit = async () => {
  if (!problem.value?.id) {
    return;
  }
  const { data, status } = await doProblemSubmitUsingPost({
    body: {
      ...form.value,
      problemId: problem.value?.id,
    },
  });
  if (status === 200) {
    if (data?.code === 0) {
      Message.success("Submit succeed");
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
</script>
<style lang="css">
#problemsView {
  max-width: 1280px;
  margin: 0 auto;
}

#problemView .arco-space-horizontal .arco-space-item {
  margin-bottom: 0 !important;
}
</style>
