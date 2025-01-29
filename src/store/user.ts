// initial state

import ACCESS_ENUM from "@/access/accessEnum";
import { StoreOptions } from "vuex";

export default {
  namespaced: true,
  state: () => ({
    loginUser: {
      userName: "notLogin",
      userRole: ACCESS_ENUM.NOT_LOGIN,
    },
  }),
  actions: {
    getLoginUser({ commit, state }, payload) {
      // todo remote login
      commit("updateUser", payload);
    },
  },
  mutations: {
    updateUser(state, payload) {
      state.loginUser = payload;
    },
  },
} as StoreOptions<any>;
