<template>
    <el-table
            ref="multipleTable"
            :data="tableData"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">
        <el-table-column
                type="selection"
                width="55">
        </el-table-column>
        <el-table-column
                label="商品信息"
                width="600" v-slot="scope">
            <el-image @click="seeDetail(scope.row.id)" style="width: 100px; height: 100px" :src="scope.row.imgPath"></el-image>
            <a @click="seeDetail(scope.row.id)">{{scope.row.name}}</a>
        </el-table-column>
        <el-table-column
                label="商家"
                width="120">
        </el-table-column>
        <el-table-column label="地址" width="200" v-slot="scope">
        </el-table-column>
        <el-table-column label="总价" width="200" v-slot="scope">
        </el-table-column>
        <el-table-column label="操作" width="200">
            <template v-slot="scope">
                <el-button
                        size="mini"
                        @click="handleBuy(scope.$index, scope.row)">退款</el-button>
                <el-button
                        size="mini"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
</template>


<script>
    import axios from "axios";
    export default {
        data() {
            return {
                tableData: [],
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
                axios.get("/api/order/selectAll/"+window.sessionStorage.getItem("id")).then(response =>{
                    this.tableData=response.data.value;
                })
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            seeDetail(id){
                this.$router.push('/detail?id='+id);
            },
            handleBuy(index,data){
                this.$confirm('确定购买'+data.number+'个['+data.name+']吗', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.buy(data);
                    axios.delete("api/cart",{params: {id: data.cartId}}).then(resp=>{
                        if(resp){
                            this.init();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消购买'
                    });
                });
            },
            buy(data){
                let data1={"address":window.sessionStorage.getItem("address"),"busmanId":data.busmanId,"customerId":window.sessionStorage.getItem("id")};
                axios.post("api/order",data1).then(response=>{
                    if(response.status==200){
                        let data2={"number":this.num,"orderId":response.data.value.id,"productId":data.id};
                        axios.post("api/orderProduct",data2).then(response=>{
                            if(response.status==200){
                            }
                        })
                    }
                })
            },
            handleChange(index,data){
                this.$nextTick(() => {
                    let data1={"id":data.cartId,"number":data.number,"productId":data.id,"userId":window.sessionStorage.getItem("id")};
                    axios.patch("api/cart", data1).then(resp => {
                        if (resp) {
                            // this.init();
                        }
                    })
                });
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