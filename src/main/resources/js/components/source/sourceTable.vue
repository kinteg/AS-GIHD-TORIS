<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Источники</p>
        <hr>
        <el-button @click="addSource" style="float: right; margin-bottom: 15px; background-color: #1ab394; border-color: #1ab394 "  type="primary" icon="el-icon-plus"></el-button>
        <div class="horizontal-scroll-wrapper  rectangles">
            <table style="display: block; overflow-x: auto; white-space: nowrap">
                <tr>
                    <th></th>
                    <th>Поставщик данных</th>
                    <th>Полное наименование набора</th>
                    <th>Краткое наименование набора</th>
                    <th>Описание</th>
                    <th>Дополнительное описание</th>
                    <th>Сфера (направление)</th>
                    <th>Периодичность актуализации </th>
                    <th>Срок обновления набора данных</th>
                    <th>Вид набора</th>
                    <th>Ключевые слова (теги)</th>
                    <th>Информационная система - источник данных</th>
                    <th>Ссылка на данные на сайте поставщика</th>
                </tr>
                <tr>
                    <td><el-button style="background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-delete"></el-button>
                        <br>
                        <br>
                        <el-button @click="updateSource" style="background-color: #1ab394; border-color: #1ab394" type="primary" size="mini" icon="el-icon-edit"></el-button></td>
                    <td><el-input placeholder="Please input" v-model="source.name"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.longName"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.shortName"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.description"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.addDescription"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.scope"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.periodicity"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.renewalPeriod"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.type"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.tags"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.providerLink"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="source.dataSource"></el-input></td>
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
    import router from "../../router/router";

    export default {
        name: "sourceTable",
        data() {
            return {
                source:{
                    name:"",
                    longName:"",
                    shortName:"",
                    description:"",
                    addDescription:"",
                    scope:"",
                    periodicity:"",
                    renewalPeriod:"",
                    type:"",
                    providerLink:"",
                    dataSource:"",
                }
            }
        },
        methods:{
            addSource(){
                router.push('create');
            },
            updateSource(){
                router.push('update');
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