import router from "@/router";
import store from "@/store";
import ACCESS_ENUM from "./accessEnum";
import checkAccess from "./checkAccess";

router.beforeEach(async (to, from, next) => {
  // get loginUser state information
  const loginUser = store.state.user.loginUser;

  // if the user hasn't logged in before, auto login
  if (!loginUser || !loginUser.userRole) {
    // get the user state information after the login process done.
    await store.dispatch("user/getLoginUser");
  }

  const needAccess = (to.meta?.access as string) ?? ACCESS_ENUM.NOT_LOGIN;

  // when jumps to a page that requires login.
  if (needAccess !== ACCESS_ENUM.NOT_LOGIN) {
    // if the user hasn't logged in, jumps to login page.
    if (!loginUser || !loginUser.userRole) {
      next("/user/login?redirect=${to.path}");
      return;
    }
    // already logged in, but need admin authority level
    if (!checkAccess(loginUser, needAccess)) {
      next("/noAuth");
      return;
    }
  }
  next();
});
