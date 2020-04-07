<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Просмотр шаблона</p>
        <div class="horizontal-scroll-wrapper  rectangles">
            <el-upload
                    class="upload-demo"
                    ref="upload"
                    action=""
                    :limit="1"
                    :on-change="sendData"
                    :auto-upload="false">
                <el-button slot="trigger" style="background-color: #1ab394; border-color: #1ab394" size="small" type="primary">Выбрать файл</el-button>
                <div class="el-upload__tip" slot="tip">Загрузить данные в таблицу</div>
            </el-upload>
            <table style=" padding: 0 5px 0 0; overflow-x: auto; ">
                <tr>
                    <th v-for="pole in showOnlyOneTable.tableModel.models">{{pole.key}}</th>
                </tr>
                <tr v-for="value in showOnlyOneTable.values.content">
                    <td v-for="oneValue in value">{{oneValue}}</td>
                </tr>
            </table>
            <el-button @click="showTableTab" style="margin-top: 10px; background-color: #1ab394; border-color: #1ab394; color: white;">Назад</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "patternTableView",
        props:['tableId'],

        mounted() {

        }
    }
</script>

<style scoped>

</style>