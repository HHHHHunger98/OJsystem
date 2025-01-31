import router from "@/router";
import store from "@/store";

router.beforeEach((to, from, next) => {
  console.log("login information", store.state.user.loginUser);
  if (to.meta?.access === "canAdmin") {
    if (store.state.user.loginUser?.role !== "admin") {
      next("/NoAuth");
      return;
    }
  }
  next();
});
