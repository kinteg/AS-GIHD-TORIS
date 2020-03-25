<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Источники</p>
        <hr>
        <div>
            <table>
                <tr>
                    <th>#</th>
                    <th>#</th>
                    <th>#</th>
                    <th>#</th>
                    <th>#</th>
                </tr>
                <tr>
                    <td><a href="sourceUpdate"><img src="../../icon/delete.svg" alt=""></a>
                        <br>
                        <button>del</button></td>
                    <td><el-input placeholder="Please input" v-model="pole1"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="pole2"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="pole3"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="pole4"></el-input></td>
                </tr>
            </table>
        </div>
        <el-pagination
                style=" text-align: center; padding-top: 10px"

                background
                layout="prev, pager, next"
                :total="1000">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        name: "sourceTable",
        data() {
            return {
                pole1:"",
                pole2:"",
                pole3:"",
                pole4:"",
            }
        }
    }
</script>

<style scoped>
    table, td, th {
        border: 1px solid #d7d7d7;
        text-align: center;
    }

    td{
        padding: 10px;
    }

    table {
        border-collapse: collapse;
        width: 100%;
    }

    th {
        height: 50px;
    }
</style>