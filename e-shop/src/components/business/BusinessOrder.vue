<template>
    <el-table
            :data="tableData"
            tooltip-effect="dark"
            style="width: 100%"
    >
        <el-table-column type="expand" prop="names">
            <template v-slot="scope">
                <el-row style="padding: 30px;border-top: 2px solid #eee;" v-for="(item,index) in orderList[scope.$index]" :key="index">
                    <el-col :span="4">
                        <el-image  @click="seeDetail(item.id)"
                                   style="width: 100px; height: 100px"
                                   :src="item.imgPath"></el-image>
                    </el-col>
                    <el-col :span="4"><div  @click="seeDetail(item.id)">{{item.name}}</div></el-col>
                    <el-col @click="seeDetail(item.id)" :span="4" style="font-size: large;font-weight: bold;color: orangered;"><div  @click="seeDetail(item.id)">￥{{item.price*item.number}}</div></el-col>
                    <el-col :span="4"><el-input-number v-model="item.number" :disabled="true"></el-input-number></el-col>
                </el-row>
                <!--                <el-table :data="orderList[scope.$index]" >-->
                <!--                    <el-table-column prop="imgPath" label="商品名称"></el-table-column>-->
                <!--                    <el-table-column prop="name" label="商品名称"></el-table-column>-->
                <!--                </el-table>-->
            </template>
        </el-table-column>

        <el-table-column prop="id" label="订单编号：" sortable align="center" ></el-table-column>
        <el-table-column prop="customer" label="用户：" sortable align="center" ></el-table-column>
        <el-table-column prop="address" label="地址：" sortable align="center" ></el-table-column>
        <el-table-column prop="totalPrice" label="总价：" sortable align="center" ></el-table-column>
        <el-table-column prop="gmtCreate" label="下单时间：" sortable align="center" ></el-table-column>
    </el-table>
</template>


<script>
    import axios from "axios";
    export default {
        data() {
            return {
                tableData: [],
                orderList: [],
                multipleSelection: []
            };
        },

        mounted() {
            this.init();
        },

        watch:{
        },

        methods: {
            init(){
                this.getData();
            },
            getData(){
                axios.get("/api/order/selectAll/busman/"+window.sessionStorage.getItem("id")).then(response =>{
                    this.tableData=response.data.value;
                    this.getItem();
                });
            },
            getItem(){
                let from=0;
                let to=this.tableData.length;
                for(;from<to;from++){
                    let self=this.tableData[from];
                    let from1=from;
                    axios.get("/api/orderProduct/selectAll/"+this.tableData[from].id).then(resp=>{
                        this.orderList.push(resp.data.value);
                        this.getName(from1, self.id);
                    })
                }
            },
            getName(from,id){
                let self=this.tableData[from];
                axios.get("/api/user/customer/"+id).then(resp=>{
                    self.customer=resp.data.value.username;
                })
            },
            seeDetail(id){
                this.$router.push('/detail?id='+id);
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleDelete(index,data){
                this.$confirm('此操作将删除['+data.name+'],是否继续', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios.delete("api/cart",{params: {id: data.cartId}}).then(resp=>{
                        if(resp){
                            this.init();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        }
    };
</script>

<style>

</style>