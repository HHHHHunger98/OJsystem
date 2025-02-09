<template>
  <div class="home">
    <MdEditor />
    <img alt="Vue logo" src="../assets/logo.png" />
    <HelloWorld msg="Welcome to Your Vue.js + TypeScript App" />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import HelloWorld from "@/components/HelloWorld.vue"; // @ is an alias to /src
import MdEditor from "@/components/MdEditor.vue";

export default defineComponent({
  name: "HomeView",
  components: {
    HelloWorld,
    MdEditor,
  },
});
</script>
