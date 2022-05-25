<template>
    <div >
        <div style="margin-top: 50px;">
            <el-col :span="8" style="margin-left: 100px;">
                <el-image style="width: 300px; height: 300px"
                           :src="good.imgPath"></el-image>
            </el-col>
            <el-col :span="8" style="padding:100px;background-color: white;">
                <div style="font-size: large;font-weight: bold">
                    {{good.name}}
                </div>
                <div style="margin-top: 30px;">
                    <a>价格</a>
                    <a style="margin-left:10px;font-size: x-large;font-weight: bold;color: orangered;">￥{{good.price}}</a>
                </div>
                <div style="margin-top: 50px;">
                    <a>数量</a>
                    <el-input-number size="mini" :min="1" :max="good.stock" v-model="num" style="margin-left: 10px"></el-input-number>
                </div>
                <div style="margin-top: 30px;align-content: center">
                    <el-button @click="buy" style="background-color: bisque;color: orangered">立即购买</el-button>
                    <el-button @click="addCart" style="background-color: orangered;color: white">加入购物车</el-button>
                </div>
            </el-col>
        </div>
        <div style="width: 800px;margin-left: 0px;">
                <a style="width: 800px;margin-left: 300px;font-size: large;background-color: white;">商品详情</a>
                <div style="margin-left: 300px;font-size: medium;background-color: white">{{good.details}}</div>

        </div>
    </div>
</template>

<script>
    import axios from "axios";
    export default {
        data() {
            return {
                good:'',
                num:1,
            };
        },

        mounted() {
            this.init();
        },

        watch:{
        },

        methods: {
            init(){
                axios.get("/api/product/single?id="+this.$route.query.id).then(response =>{
                    this.good=response.data.value;
                })
            },
            buy(){
                let data={"address":window.sessionStorage.getItem("address"),"busmanId":this.good.busmanId,"customerId":window.sessionStorage.getItem("id")};
                axios.post("api/order",data).then(response=>{
                    if(response.status==200){
                        let data1={"number":this.num,"orderId":response.data.value.id,"productId":this.good.id};
                        axios.post("api/orderProduct",data1).then(response=>{
                            if(response.status==200){
                                this.$router.push('/order');
                            }
                        })
                    }
                })
            },
            addCart(){
                let data={"productId":this.good.id,"number":this.num,"userId":window.sessionStorage.getItem("id")};
                axios.post("api/cart",data).then(response=>{
                    if(response.status==200){
                        this.$router.push('/cart');
                    }
                })
            }
        }
    };
</script>

<style>

</style>