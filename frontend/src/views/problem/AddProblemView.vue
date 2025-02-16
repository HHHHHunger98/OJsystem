<template>
  <div id="addProblemView" style="align-items: center">
    <h2 style="text-align: center">create a new problem</h2>
    <a-form :model="form">
      <a-form-item field="title" label="Problem Title">
        <a-input
          v-model="form.title"
          placeholder="Please input a title"
          style="max-width: 400px"
        />
      </a-form-item>
      <a-form-item field="tags" label="Tags">
        <a-input-tag
          v-model="form.tags"
          placeholder="Please select the tags"
          allow-clear
          style="max-width: 400px"
        />
      </a-form-item>
      <a-form-item field="content" label="Content">
        <MdEditor :value="form.content" :handle-change="contentValueChange" />
      </a-form-item>
      <a-form-item field="solution" label="Solution">
        <MdEditor :value="form.solution" :handle-change="solutionValueChange" />
      </a-form-item>
      <a-form-item
        label="Judge Config"
        :content-flex="false"
        :merge-props="false"
      >
        <a-space direction="vertical" style="min-width: 480px">
          <a-form-item field="JudgeConfig.TimeLimit" label="Time Limit">
            <a-input-number
              :style="{ width: '320px' }"
              placeholder="Please Enter The Time Limit"
              v-model="form.judgeConfig.timeLimit"
              :default-value="500"
              mode="button"
              min="0"
            />
          </a-form-item>
          <a-form-item field="JudgeConfig.SpaceLimit" label="Space Limit">
            <a-input-number
              :style="{ width: '320px' }"
              placeholder="Please Enter The Space Limit"
              v-model="form.judgeConfig.spaceLimit"
              :default-value="500"
              mode="button"
              min="0"
            />
          </a-form-item>
          <a-form-item field="JudgeConfig.StackLimit" label="Stack Limit">
            <a-input-number
              :style="{ width: '320px' }"
              placeholder="Please Enter The Stack Limit"
              v-model="form.judgeConfig.stackLimit"
              mode="button"
              min="0"
            />
          </a-form-item>
        </a-space>
      </a-form-item>

      <a-form-item
        label="Judge Case"
        :content-flex="false"
        :merge-props="false"
        style="align-items: baseline"
      >
        <a-space direction="vertical">
          <a-form-item
            v-for="(judgeCaseItem, index) of form.judgeCase"
            :field="`form.judgeCase[${index}]`"
            :label="`Judge Case ${index}`"
            :key="index"
          >
            <a-space>
              <a-form-item
                :field="`form.judgeCase[${index}].input`"
                :label="`Input`"
                :key="index"
              >
                <a-input
                  v-model="judgeCaseItem.input"
                  placeholder="please enter the input case"
                />
              </a-form-item>
              <a-form-item
                :field="`form.judgeCase[${index}].output`"
                :label="`Output`"
                :key="index"
              >
                <a-input
                  v-model="judgeCaseItem.output"
                  placeholder="please enter the output case"
                />
              </a-form-item>
              <a-button @click="handleDelete(index)" status="danger"
                >Delete</a-button
              >
            </a-space>
          </a-form-item>

          <a-button @click="handleAdd" type="dashed" status="success"
            >Add Test Case</a-button
          >
        </a-space>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" style="min-width: 200px" @click="doSubmit"
          >Submit</a-button
        >
      </a-form-item>
    </a-form>
  </div>
</template>
<script setup lang="ts">
import MdEditor from "@/components/MdEditor.vue";
import {
  addProblemUsingPost,
  getProblemByIdUsingGet,
  updateProblemUsingPost,
} from "../../../generated/index";
import { onMounted, ref } from "vue";
import { Message } from "@arco-design/web-vue";
import { useRoute } from "vue-router";

const route = useRoute();
const updatePage = route.path.includes("update");

let form = ref({
  title: "",
  content: "",
  judgeCase: [
    {
      input: "",
      output: "",
    },
  ],
  judgeConfig: {
    spaceLimit: 1000,
    stackLimit: 1000,
    timeLimit: 1000,
  },
  solution: "",
  tags: [],
});

/**
 * Get problem data to be updated by Id
 */
const loadData = async () => {
  const problemId = route.query.id as any;
  if (!problemId) {
    return;
  } else {
    const res = await getProblemByIdUsingGet({
      query: {
        id: problemId,
      },
    });
    if (res.status === 200) {
      if (res.data?.code === 0) {
        /**
         * JSON to js object
         * Best way to do the conversion is to put it at the backend,
         * implement a API for requesting problem data by id and handling the type conversion
         */
        form.value = res.data.data as any;
        if (!form.value.judgeConfig) {
          form.value.judgeConfig = {
            spaceLimit: 1000,
            stackLimit: 1000,
            timeLimit: 1000,
          };
        } else {
          form.value.judgeConfig = JSON.parse(form.value.judgeConfig as any);
        }
        if (!form.value.judgeCase) {
          form.value.judgeCase = [
            {
              input: "",
              output: "",
            },
          ];
        } else {
          form.value.judgeCase = JSON.parse(form.value.judgeCase as any);
        }
        if (!form.value.tags) {
          form.value.tags = [];
        } else {
          form.value.tags = JSON.parse(form.value.tags as any);
        }
      } else {
        Message.error("Get problem data failed" + res.data?.message);
      }
    } else {
      Message.error("No response from server");
    }
  }
};

onMounted(() => {
  loadData();
});

const contentValueChange = (v: string) => {
  form.value.content = v;
};
const solutionValueChange = (v: string) => {
  form.value.solution = v;
};

/**
 * Add new judge case
 */
const handleAdd = () => {
  if (form.value.judgeCase) {
    form.value.judgeCase.push({
      input: "",
      output: "",
    });
  }
};
/**
 * Delete judge case
 */
const handleDelete = (index: number) => {
  if (form.value.judgeCase) {
    form.value.judgeCase.splice(index, 1);
  }
};

const doSubmit = async () => {
  console.log(form.value);

  if (updatePage) {
    const res = await updateProblemUsingPost({
      body: form.value,
    });
    console.log(res);
    if (res.status === 200) {
      if (res.data?.code === 0) {
        Message.success("Problem updating succeed");
        console.log("okay");
      } else {
        Message.error("Problem updating failed");
      }
    } else {
      Message.error("No response from server");
    }
  } else {
    const res = await addProblemUsingPost({
      body: form.value,
    });
    console.log(res);
    if (res.status === 200) {
      if (res.data?.code === 0) {
        Message.success("Problem adding succeed");
        console.log("okay");
      } else {
        Message.error("Problem adding failed");
      }
    } else {
      Message.error("No response from server");
    }
  }
};
</script>
<style lang=""></style>
