<template>
  <div id="userLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header">
        <a-space>
          <img src="../assets/LogoOJ.png" class="log" />
          <div>My OJ</div>
        </a-space>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">
        <a href="https://github.com/HHHHHunger98/OJsystem" target="_blank">
          Online Judge System by HHHHHunger98
        </a>
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<style scoped>
#userLayout {
  text-align: center;
  background: url("../assets/BGUserLayout.png");
}

#userLayout .log {
  width: 128px;
  height: 128px;
}

#userLayout .header {
  margin-top: 100px;
  margin-bottom: 20px;
  font-size: 32px;
}
#userLayout .content {
  margin-bottom: 20px;
  padding: 30px;
}
#userLayout .footer {
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
  backdrop-filter: blur(10px);
}
</style>
