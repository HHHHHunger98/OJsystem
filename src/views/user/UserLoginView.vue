<template>
  <div id="userLoginView">
    <h1 style="margin-bottom: 32px">Welcome to my Online Judge System!</h1>
    <a-form
      style="max-width: 480px; margin: 0 auto"
      label-align="left"
      auto-label-width
      :model="form"
      @submit="handleSubmit"
    >
      <a-form-item
        field="userAccount"
        tooltip="Password must be at least 8 characters."
        label="account"
      >
        <a-input
          v-model="form.userAccount"
          placeholder="please enter your user account"
        />
      </a-form-item>
      <a-form-item field="userPassword" label="userPassword">
        <a-input-password
          v-model="form.userPassword"
          placeholder="please enter your password"
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 200px"
          >Login</a-button
        >
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { Message } from "@arco-design/web-vue";
import { userLoginUsingPost, UserLoginRequest } from "../../../generated/index";
import { reactive } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

const form = reactive({
  userAccount: "",
  userPassword: "",
} as UserLoginRequest);

const router = useRouter();
const store = useStore();
/**
 * submit form data
 */
const handleSubmit = async () => {
  const { data, error } = await userLoginUsingPost({
    body: form,
  });
  if (error) {
    console.log(error);
    return;
  }

  // Login succeed
  if (data.code === 0) {
    await store.dispatch("user/getLoginUser");
    router.push({
      path: "/",
      replace: true,
    });
  } else {
    // Login failed
    Message.error("login failed:" + data.message);
  }
};
</script>
