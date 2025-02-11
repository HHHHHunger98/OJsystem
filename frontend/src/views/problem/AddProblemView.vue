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
              :default-value="500"
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
  ProblemAddRequest,
} from "../../../generated/index";
import { reactive } from "vue";
import { Message } from "@arco-design/web-vue";

const contentValueChange = (v: string) => {
  form.content = v;
};
const solutionValueChange = (v: string) => {
  form.solution = v;
};

const form = reactive<ProblemAddRequest>({
  title: "A+B",
  content: "hhhahahah",
  judgeCase: [
    {
      input: "1,2",
      output: "3,4",
    },
  ],
  judgeConfig: {
    spaceLimit: 1000,
    stackLimit: 1000,
    timeLimit: 1000,
  },
  solution: "your",
  tags: ["java", "easy"],
});

/**
 * Add new judge case
 */
const handleAdd = () => {
  if (form.judgeCase) {
    form.judgeCase.push({
      input: "",
      output: "",
    });
  }
};
/**
 * Delete judge case
 */
const handleDelete = (index: number) => {
  if (form.judgeCase) {
    form.judgeCase.splice(index, 1);
  }
};

const doSubmit = async () => {
  console.log(form);
  const res = await addProblemUsingPost({
    body: form,
  });
  console.log(res);
  if (res.status === 200) {
    if (res.data.code === 0) {
      Message.success("Problem adding succeed");
      console.log("okay");
    } else {
      Message.error("Problem adding failed");
    }
  } else {
    Message.error("No response from server");
  }
};
</script>
<style lang=""></style>
