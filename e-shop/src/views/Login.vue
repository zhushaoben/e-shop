<template>
    <div>
        <el-form ref="loginForm" :model="loginForm" :rules="rules" class="loginContainer">
            <h3 class="loginTitle">系统登录</h3>
            <el-form-item prop="username">
                <el-input type="text" auto-complete="false" v-model="loginForm.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" auto-complete="false" v-model="loginForm.password" show-password placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" style="width: 100%" @click="submitLogin">登录</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" style="width: 100%;background-color: orangered" @click="Register">注册</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    import axios from "axios";
    export default {
        name: "Login",
        data(){
            const pwdCheck = async(rule, value, callback) => {
                if (value.length < 6) {
                    return callback(new Error('密码不能少于6位！'));
                } else if (value.length > 16) {
                    return callback(new Error('密码最长不能超过16位！'));
                } else {
                    callback()
                }
            };
            return{
                loginForm:{
                    username:'',
                    password:''
                },
                rules: {
                    username:[{required: true, message:'用户名不能为空',trigger: 'blur'}],
                    password: [{required: true, validator: pwdCheck,trigger: 'blur'}]
                },
            }
        },
        methods:{
            submitLogin(){
                this.$refs.loginForm.validate((valid) => {
                   if(valid){
                       axios.get("/api/user/login/"+this.loginForm.username+"/"+this.loginForm.password,{withCredentials: true})
                          .then(resp=>{
                          if(resp){
                              if(resp.status == 200){
                                  window.sessionStorage.setItem("token",resp.data.value.token);
                                  this.jump();
                              }
                          }
                      })
                   }
                   else{
                       this.$message.error('请输入所有字段');
                       return false;
                   }
                });
            },
            jump(){
                axios.get("/api/user/login/getUser",{withCredentials: true})
                    .then(resp=>{
                        if(resp){
                            // window.sessionStorage.setItem("token")
                            if(resp.status == 200) {
                                window.sessionStorage.setItem("username",resp.data.value.username);
                                window.sessionStorage.setItem("name",resp.data.value.name);
                                window.sessionStorage.setItem("id",resp.data.value.id);
                                window.sessionStorage.setItem("address",resp.data.value.address);
                                window.sessionStorage.setItem("phone",resp.data.value.phone);
                                window.sessionStorage.setItem("email",resp.data.value.email);
                                window.sessionStorage.setItem("password",resp.data.value.password);
                                // alert(window.sessionStorage.getItem("phone"));
                                if (resp.data.value.type == 2){
                                    this.$router.replace('/manager');
                                }
                                if(resp.data.value.type == 1){
                                    this.$router.replace('/business');
                                }
                                if(resp.data.value.type == 0){
                                    this.$router.replace('/user');
                                }
                            }
                        }
                    })
            },
            Register(){
                this.$router.replace('/register');
            }
        }
    }
</script>

<style>
    .loginContainer{
      border-radius: 15px;
      background-clip: padding-box;
      margin: 180px auto;
      width: 350px;
      padding: 15px 35px 15px 35px;
      background: #fff;
      border: 1px solid #eaeaea;
      box-shadow:0 0 25px #cac6c6;
    }
    .loginTitle{
        margin: 0px auto 40px auto;
        text-align: center;
    }
</style>