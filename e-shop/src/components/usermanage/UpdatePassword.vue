<!--  -->
<template>

    <div style="margin-left: 35%;">
        <el-card class="box-card" style="text-align: center">
            <div slot="header" class="clearfix">
                <span>修改个人信息</span>
            </div>
            <el-form
                ref="loginFormRef"
                :rules="loginRules"
                :model="loginForm"
                class="login_form"
                label-width="100px"
            >
                <el-form-item label="昵称" prop="name">
                    <el-input
                            v-model="loginForm.name"
                            type="text"
                    ></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱" prop="email">
                <el-input
                        v-model="loginForm.email"
                        type="text"
                ></el-input>
                </el-form-item>
                <el-form-item label="电话号码" prop="phone">
                <el-input
                        v-model="loginForm.phone"
                        type="text"
                ></el-input>
                  </el-form-item>
                <el-form-item label="地址" prop="address">
                    <el-input
                              v-model="loginForm.address"
                              type="text"
                    ></el-input>
                </el-form-item>
                <el-form-item label="输入原密码" prop="oldpassword">
                    <el-input show-password
                        v-model="loginForm.oldpassword"
                        type="password"
                    ></el-input>
                </el-form-item>

              <el-form-item label="输入新密码" prop="password">
                <el-input show-password
                    v-model="loginForm.password"
                    type="password"
                ></el-input>
              </el-form-item>

                <el-form-item label="确认新密码" prop="repassword">
                    <el-input show-password
                        v-model="loginForm.repassword"
                        type="password"
                    ></el-input>
                </el-form-item>


                <el-form-item class="btns">
                    <el-button type="primary" @click="handleLogin"
                        >提交</el-button
                    >
                    <el-button type="success" @click="handleReset"
                        >重置</el-button
                    >
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script>
import axios from "axios";
export default {
    name: "UpdatePassword",
  data: function () {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        callback();
      }
    };

    var validatePass2 = (rule, value, callback) => {
        callback();
    };

    var validatePass3 = (rule, value, callback) => {
          if (value === "") {
            callback();
          } else if (value !== this.loginForm.password) {
            callback(new Error("两次输入密码不一致!"));
          } else {
            callback();
          }
          }
    return {
      username: "",
      loginForm: {
          id:window.sessionStorage.getItem("id"),
        name: window.sessionStorage.getItem("name"),
        oldpassword: "",
        repassword: "",
        password: "",
          address:window.sessionStorage.getItem("address"),
          phone:window.sessionStorage.getItem("phone"),
          email:window.sessionStorage.getItem("email"),
      },

      loginRules: {
        oldpassword: [
          {validator: validatePass, trigger: "blur"},
          {required: true, message: "请输入原来的密码"},
        ],
        password: [
          {
            validator: validatePass2,
            required: false,
            message: "请输入新的密码名称",
            trigger: "blur",
          },
        ],
        repassword: [
          {validator: validatePass3, trigger: "blur"},
          {required: false, message: "请确认新的密码"},
        ],
      },
    };
  },
    created() {},
    methods: {
        handleLogin() {
            this.$refs.loginFormRef.validate(async (valid) => {
                if (valid) {
                    if (this.loginForm.oldpassword != window.sessionStorage.getItem("password")) {
                        this.$message.success("原密码错误！");
                        return;
                    }
                    //如果验证成功
                    //首相在这个地方，返回的是一个对象，而且这个地想里面含有很多数据，
                    //在这个地方主要使用的就是这个解析的语法，在这个地方就可以很好的使用这个语法了，然后就可以很好的之后的访问了
                    if(this.loginForm.password==''){
                        this.loginForm.password=window.sessionStorage.getItem("password");
                    }
                    axios.patch("api/user",this.loginForm).then(res=>{
                        if (res.status == 200) {
                            window.sessionStorage.setItem("name",res.data.value.name);
                            window.sessionStorage.setItem("address",res.data.value.address);
                            window.sessionStorage.setItem("phone",res.data.value.phone);
                            window.sessionStorage.setItem("email",res.data.value.email);
                            window.sessionStorage.setItem("password",res.data.value.password);
                            this.$message.success("修改成功！"); //信息提示
                            this.handleReset();
                        } else {
                            this.$message.success("修改失败！"); //信息的提示
                        }
                    });


                } else {
                    //如果失败
                    return;
                }
            });
        },
        handleReset() {
            this.$refs.loginFormRef.resetFields();
            this.loginForm.address=window.sessionStorage.getItem("address");
            this.loginForm.name=window.sessionStorage.getItem("name");
            this.loginForm.email=window.sessionStorage.getItem("email");
            this.loginForm.phone=window.sessionStorage.getItem("phone");
        },
    },
};
</script>

<style  scoped>
.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}
.clearfix:after {
    clear: both;
}

.box-card {
    width: 400px;
}
</style>
