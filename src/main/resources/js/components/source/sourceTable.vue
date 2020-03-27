<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Источники</p>
        <hr>
        <el-button @click="deleteSomeSource"  style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-delete"></el-button>
        <el-button @click="addSource" style="float: right; margin-bottom: 15px; background-color: #1ab394; border-color: #1ab394 "  type="primary" icon="el-icon-plus"></el-button>
        <div class="horizontal-scroll-wrapper  rectangles">
            <table style="display: block; overflow-x: auto; white-space: nowrap">
                <tr>
                    <th></th>
                    <th><el-checkbox ></el-checkbox></th>
                    <th>Номер</th>
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
                    <th>Архивность</th>
                    <th>Дата создания</th>
                    <th>Дата деактивации</th>
                    <th>Дата активации</th>
                    <th>Последнее обновление</th>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td><el-input placeholder="Please input" v-model="source.id"></el-input></td>
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
                    <td><el-input placeholder="Please input" v-model="source.isArchive"></el-input></td>
                </tr>
                <tbody v-for="source in sourceData">
                <tr >
                    <td>
                        <el-button @click="deleteOneSource(source.id)"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-delete"></el-button>
                        <br>
                        <el-button @click="updateSource(source.id)" style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394" type="primary" size="mini" icon="el-icon-edit"></el-button>
                        <br>
                        <el-button @click="sourceView(source.id)" style="background-color: #1ab394; border-color: #1ab394" type="primary" size="mini" icon="el-icon-view"></el-button>
                    </td>
                    <td> <el-checkbox @change="check(source.id)"></el-checkbox></td>
                    <td>{{source.id}}</td>
                    <td>{{source.name}}</td>
                    <td>{{source.longName}}</td>
                    <td>{{source.shortName}}</td>
                    <td>{{source.description}}</td>
                    <td>{{source.addDescription}}</td>
                    <td>{{source.scope}}</td>
                    <td>{{source.periodicity}}</td>
                    <td>{{source.renewalPeriod}}</td>
                    <td>{{source.type}}</td>
                    <td>{{source.tags}}</td>
                    <td>{{source.providerLink}}</td>
                    <td>{{source.dataSource}}</td>
                    <td>{{source.isArchive ? "Да" : "Нет"}}</td>
                    <td>{{source.dateCreation}}</td>
                    <td>{{source.dateDeactivation}}</td>
                    <td>{{source.dateActivation}}</td>
                    <td>{{source.lastUpdate}}</td>
                </tr>
                </tbody>

            </table>
        </div>
        <my-pagination
                :page-size="pagination.pageSize"
                :current-page="pagination.currentPage"
                :totalPages="pagination.totalPages"
                :totalElements="pagination.totalElements"
                @onCurrentChange="onCurrentChange"
                @onSizeChange="onSizeChange"/>
    </div>
</template>

<script>
    import router from "../../router/router";
    import {AXIOS} from "../../AXIOS/http-common";
    import MyPagination from "../general/pagination.vue";
    export default {
        name: "sourceTable",
        components: {MyPagination},
        data() {
            return {
                url:"source/getAll?",
                sourceData: "",
                pagination:{
                    pageSize: 10,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                },
                source:{
                    check:[],
                    id:"",
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
                    isArchive:"",
                    dateCreation:"",
                    dateDeactivation:"",
                    dateActivation:"",
                    lastUpdate:"",

                }
            }
        },
        methods:{

            check(id){
                let key = this.source.check.indexOf(id);
                if(key !== -1){
                    this.source.check.splice(key,1);
                } else {
                    this.source.check.push(id);
                }
            },



            deleteSource(id){
                AXIOS.get("source/archive/" + id).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Источник был архивирован','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Источник не был архивирован','error');
                    }
                });
            },

            deleteOneSource(id){
                this.deleteSource(id);
            },

            deleteSomeSource(){
                if(this.source.check.length !== 0){
                    for(let i = 0; i < this.source.check.length; i++){
                        this.deleteSource(this.source.check[i]);
                    }
                    this.updatePage();
                } else {
                    this.notify('Ошибка','Выберите источники которые хотите архивировать','error');
                }
            },

            onCurrentChange(value) {
                this.currentPage = value;
                AXIOS.get(this.url + '&page=' + (value - 1))
                    .then(response => {
                        this.sourceData = response.data.content;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            },

            onSizeChange(value) {
                this.pageSize = value;
                this.currentPage = 1;
                AXIOS.get(this.url + '&size=' + value)
                    .then(response => {
                        this.sourceData = response.data.content;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            },

            addSource(){
                router.push('create');
            },

            updateSource(id){
                router.push('update/'+ id);
            },

            sourceView(id){
                router.push('view/'+ id);
            },

            notify(title,message,type){
                this.$notify({
                    title: title,
                    message: message,
                    type: type
                });
            },

            updatePage(){
                let formData = new FormData();
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage);
                formData.append("sort","desc");
                formData.append("key","name");
                formData.append("name","Test");

                AXIOS.get("source/getAll").then(response => {
                    this.pagination.totalPages = response.data.totalPages;
                    this.pagination.totalElements = response.data.totalElements;
                    this.sourceData = response.data.content;
                })
            }
        },
        mounted() {
            let formData = new FormData();
            formData.append("size",this.pagination.pageSize);
            formData.append("page",this.pagination.currentPage - 1);
            formData.append("sort","");


            AXIOS.get("source/getAll").then(response => {
                this.pagination.totalPages = response.data.totalPages;
                this.pagination.totalElements = response.data.totalElements;
                this.sourceData = response.data.content;
            })
        }
    }
</script>

<style scoped>
    table, td, th {
        border: 1px solid #d7d7d7;
        text-align: center;
    }

    th{
        padding: 10px;
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