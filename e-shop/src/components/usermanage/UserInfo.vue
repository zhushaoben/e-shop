<!--  -->
<template>
    <div>

        <el-descriptions  title="我的信息" border="true" column="1" style="width:50%;box-sizing: border-box;font-size: 15px;color: #ffffff;">
            <el-descriptions-item labelStyle="width: 50px"
                                  contentStyle="width: 250px" label="用户名"
                >{{this.username}}</el-descriptions-item
            >
            <el-descriptions-item label="昵称"
                >{{this.name}}</el-descriptions-item
            >
            <el-descriptions-item label="地址"
                >{{this.address}}</el-descriptions-item
            >
            <el-descriptions-item label="电子邮箱">{{this.email}}</el-descriptions-item>
            <el-descriptions-item label="电话">{{this.phone}}</el-descriptions-item>
        </el-descriptions>
    </div>
</template>

<script>
export default {
    name: "userinfo",
    data(){
        return{
            username:window.sessionStorage.getItem("username"),
            name:window.sessionStorage.getItem("name"),
            address:window.sessionStorage.getItem("address"),
            phone:window.sessionStorage.getItem("phone"),
            email:window.sessionStorage.getItem("email"),
        }
    }

};
</script>

<style  scoped>
</style>
