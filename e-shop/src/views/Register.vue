<template>
    <div>
        <el-form ref="registerForm" :model="registerForm" :rules="rules" class="registerContainer">
            <h3 class="registerTitle">用户注册</h3>
            <div>
                <el-radio-group v-model="registerForm.role">
                    <el-radio-button label="用户"></el-radio-button>
                    <el-radio-button label="商家"></el-radio-button>
                </el-radio-group>
            </div>
            <el-form-item prop="username">
                <el-input type="text" auto-complete="false" v-model="registerForm.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="username">
                <el-input type="text" auto-complete="false" v-model="registerForm.name" placeholder="请输入昵称/店铺名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" auto-complete="false" v-model="registerForm.password" show-password placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="againPassword">
                <el-input type="password" auto-complete="false" v-model="registerForm.againPassword" show-password placeholder="请重复密码"></el-input>
            </el-form-item>
            <el-form-item prop="phone">
                <el-input type="text" auto-complete="false" v-model="registerForm.phone" placeholder="请输入手机号"></el-input>
            </el-form-item>
            <el-form-item prop="email">
                <el-input type="text" auto-complete="false" v-model="registerForm.email"  placeholder="请输入邮箱"></el-input>
            </el-form-item>
            <el-form-item prop="address">
                <el-input type="text" auto-complete="false" v-model="registerForm.address" placeholder="请输入地址"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" style="width: 100%" @click="submitRegister">注册</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" style="width: 100%;background-color: orangered" @click="Login">返回登陆登录界面</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    import axios from "axios";
    export default {
        name: "Login",
        data(){
            // 密码验证
            const pwdCheck = async(rule, value, callback) => {
                if (value.length < 6) {
                    return callback(new Error('密码不能少于6位！'));
                } else if (value.length > 16) {
                    return callback(new Error('密码最长不能超过16位！'));
                } else {
                    callback()
                }
            };
            // 重复密码验证
            const pwdAgainCheck = async(rule, value, callback) => {
                if (value.length < 1) {
                    return callback(new Error('重复密码不能为空！'));
                } else if(this.registerForm.password != this.registerForm.againPassword){
                    return callback(new Error('两次输入密码不一致！'));
                }else{
                    callback()
                }
            };
            const phoneCheck = async(rule, value, callback) => {
                if (value.length != 11) {
                    return callback(new Error('格式错误'));
                } else {
                    let reg = /^1[3456789]\d{9}$/;
                    if (!reg.test(value)) {
                        return callback(new Error('格式错误'));
                    }
                }
                callback();
            };
            const emailCheck = async(rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请正确填写邮箱'));
                } else {
                    if (value !== '') {
                        var reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                        if(!reg.test(value)){
                            callback(new Error('请输入有效的邮箱'));
                        }
                    }
                    callback();
                }
            };
            return{
                registerForm:{
                    username:'',
                    name:'',
                    password:'',
                    againPassword:'',
                    address:'',
                    phone:'',
                    email:'',
                    type: 0,
                    role:'用户',
                    money:0
                },
                rules: {
                    username:[{required: true, message:'用户名不能为空',trigger: 'blur'}],
                    password: [{required: true, validator: pwdCheck,trigger: 'blur'}],
                    againPassword: [{required: true, validator: pwdAgainCheck,trigger: 'blur'}],
                    phone: [{required: true, validator: phoneCheck,trigger: 'blur'}],
                    email: [{required: true, validator: emailCheck,trigger: 'blur'}]
                },
            }
        },
        watch:{
            "registerForm.role":function (newval,oldval){
                if(newval=='用户')this.registerForm.type=0;
                if(newval=='商家')this.registerForm.type=1;
                alert("您正在注册"+newval);
            },
        },
        methods:{
            submitRegister(){
                this.$refs.registerForm.validate((valid) => {
                   if(valid){
                       if(this.registerForm.role=='用户')this.registerForm.type=0;
                       axios.post("/api/user/register",this.registerForm,{withCredentials: true})
                          .then(resp=>{
                          if(resp){
                              if(resp.status == 200){

                                  this.Login();
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
            Login(){
                this.$router.replace('/');
            }
        }
    }
</script>

<style>
    .registerContainer{
      border-radius: 15px;
      background-clip: padding-box;
      margin: 180px auto;
      width: 350px;
      padding: 15px 35px 15px 35px;
      background: #fff;
      border: 1px solid #eaeaea;
      box-shadow:0 0 25px #cac6c6;
    }
    .registerTitle{
        margin: 0px auto 40px auto;
        text-align: center;
    }
</style>