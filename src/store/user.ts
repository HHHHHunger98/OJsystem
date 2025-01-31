// initial state

import ACCESS_ENUM from "@/access/accessEnum";
import { StoreOptions } from "vuex";
import { getLoginUserUsingGet } from "../../generated";

export default {
  namespaced: true,
  state: () => ({
    loginUser: {
      userName: "notLogin",
    },
  }),
  actions: {
    async getLoginUser({ commit, state }, payload) {
      // todo remote login
      const res = await getLoginUserUsingGet();
      if (res.status === 200) {
        commit("updateUser", res.data);
      } else {
        commit("updateUser", {
          ...state.loginUser,
          userRole: ACCESS_ENUM.NOT_LOGIN,
        });
      }
      console.log(res.status);
    },
  },
  mutations: {
    updateUser(state, payload) {
      state.loginUser = payload;
    },
  },
} as StoreOptions<any>;
