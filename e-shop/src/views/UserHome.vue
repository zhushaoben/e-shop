
<template >
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="title" @click="searchClick">电子商城管理系统</div>
                <div>
                    <el-dropdown>
                        <el-button type="primary" style="color: white;margin-right: 20px" >
                            {{ name }}<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="userInfoClick">个人主页</el-dropdown-item>
                            <el-dropdown-item @click.native="updateInfoClick">修改个人信息</el-dropdown-item>
                            <el-dropdown-item @click.native="cartClick">我的购物车</el-dropdown-item>
                            <el-dropdown-item @click.native="orderClick">我的订单</el-dropdown-item>
                            <el-dropdown-item style="background-color: orangered;color: white" @click.native="logout">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>

                </div>
            </el-header>
            <el-main >
                <router-view />
            </el-main>
        </el-container>
    </div>
</template>

<script>
    import axios from "axios";
    import SearchContent from "@/components/usermanage/SearchContent";

    export default {
        components: { SearchContent},
        data(){
            return{
                name: window.sessionStorage.getItem("name")
            }
        },
        name:"ManagerHome",
        mounted() {
            this.menuClick();
        },
        methods:{
            logout() {
                axios.delete("api/user/logout",{data: window.sessionStorage.getItem("token")}).then(resp=>{
                    if(resp){
                        this.$message.success("退出成功!");
                        localStorage.clear();
                        this.$router.replace("/");
                    }
                })
            },
            menuClick(){
                this.$router.push("/search");
            },
            updateInfoClick(){
                this.$router.push("/updateinfo");
            },
            userInfoClick(){
                this.$router.push("/userinfo");
            },
            cartClick(){
                this.$router.push("/cart");
            },
            orderClick(){
                this.$router.push("/order");
            },
            searchClick(){
                this.$router.push("/search");
            }
        }
    }
</script>

<style>
    .homeHeader{
        background: #409EFF;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0 15px;
        box-sizing: border-box;
    }
    .homeHeader .title{
        font-size: 30px;
        font-family: 华文行楷;
        color: white;
    }
</style>