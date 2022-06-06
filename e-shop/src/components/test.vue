<div id="app">
    <el-table :data="list"
              height="500"
              @expand-change="expandChange"
              style="width: 100%">
        <el-table-column type="expand" prop="names">
            <template slot-scope="scope" >
                <el-table :data="scope.row.names" >
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column prop="city_name" label="商品图片"></el-table-column>
                    <el-table-column prop="price" label="商品名称"></el-table-column>
                    <el-table-column prop="sale_price" label="规格"></el-table-column>
                    <el-table-column prop="discount_price" label="数量"></el-table-column>
                    <el-table-column prop="product_online_time" label="发货状态"></el-table-column>
                    <el-table-column prop="product_off_time" label="发货时间"></el-table-column>
                    <el-table-column prop="product_off_time" label="物流信息"></el-table-column>
                    <el-table-column fixed="right" align="center" width="100" label="操作">
                        <el-button>全部发货</el-button>
                        <el-button>部分发货</el-button>
                    </el-table-column>
                </el-table>
            </template>
        </el-table-column>
        <el-table-column prop="Id" label="订货编号：" sortable align="center" >
            <template slot-scope="scope">
                <span>{{scope.row.Id}}</span>
            </template>
        </el-table-column>
        <el-table-column prop="Id" label="下单用户昵称：" sortable align="center" >
            <template slot-scope="scope">
                <span>{{scope.row.name}}</span>
            </template>
        </el-table-column>
        <el-table-column prop="Id" label="下单用户手机号码：" sortable align="center" >
            <template slot-scope="scope">
                <span>{{scope.row.phone}}</span>
            </template>
        </el-table-column>
        <el-table-column prop="Id" label="下单时间：" sortable align="center" >
            <template slot-scope="scope">
                <span>{{scope.row.time}}</span>
            </template>
        </el-table-column>
        <el-table-column fixed="right" align="center" width="100" label="操作">
            <el-button>全部发货</el-button>
            <el-button>部分发货</el-button>
        </el-table-column>
    </el-table>
</div>
<script>
    export default {
        data() {
            return {
                list: [
                    {
                        Id: '1店',
                        name: "小红",
                        phone: "13076984413",
                        time: "2019-11-11 12:22:22",
                        names: [
                            {name: 'apple', num: 1},
                            {name: 'banana', num: 2}]
                    },
                    {
                        Id: '2店',
                        name: "小红",
                        phone: "13076984413",
                        time: "2019-11-11 12:22:22",
                        names: [{
                            name: 'apple',
                            num: 1
                        }]
                    }
                ]
            }
        }
    }
</script>
