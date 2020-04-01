<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Шаблоны
            <el-button class="trt" @click="deleteSomePattern"  style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-delete"></el-button>
            <el-button @click="deArchiveSomePattern"  style="float: right; margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-upload2"></el-button>
        <div class="horizontal-scroll-wrapper  rectangles">
            <table style="display: block; overflow-x: auto; ">
                <tr>
                    <th></th>
                    <th><el-checkbox ></el-checkbox></th>
                    <th @click="sort('id')">Номер</th>
                    <th @click="sort('name')">Навание</th>
                    <th @click="sort('description')">Описание</th>
                    <th @click="sort('direction')">Направление </th>
                    <th @click="sort('management')">Ответсвенный за ведение </th>
                    <th @click="sort('archive')">Архивность</th>
                    <th @click="sort('date_creation')">Дата создания</th>
                    <th @click="sort('date_deactivation')">Дата деактивации</th>
                    <th @click="sort('date_activation')">Дата активации</th>
                    <th @click="sort('last_update')">Последнее обновление</th>
                </tr>
                <tr>
                    <td><el-button @click="sort('')"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-search"></el-button></td>
                    <td></td>
                    <td><el-input placeholder="Please input" v-model="pattern.id"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="pattern.name"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="pattern.description"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="pattern.direction"></el-input></td>
                    <td><el-input placeholder="Please input" v-model="pattern.management"></el-input></td>
                    <td>
                        <el-select v-model="value" placeholder="Select">
                            <el-option
                                    v-for="item in options"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </td>
                    <td> <div class="block">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="pattern.dateCreation"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
                    <td> <div class="block">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="pattern.dateDeactivation"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
                    <td> <div class="block">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="pattern.dateActivation"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
                    <td> <div class="block">
                        <el-date-picker
                                value-format="yyyy-MM-dd"
                                v-model="pattern.lastUpdate"
                                type="daterange"
                                range-separator="To"
                                start-placeholder="Start date"
                                end-placeholder="End date">
                        </el-date-picker>
                    </div></td>
                </tr>
                <tbody v-for="pattern in patternData">
                <tr>
                    <td>
                         <span v-if="pattern.isArchive">
                             <el-button @click="deArchiveOnePattern(pattern.id)"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-upload2"></el-button>
                         </span>
                        <span v-else>
                            <el-button @click="deleteOnePattern(pattern.id)"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-delete"></el-button>
                         </span>
                    </td>
                    <td> <el-checkbox @change="check(pattern.id)"></el-checkbox></td>
                    <td>{{pattern.id}}</td>
                    <td>{{pattern.name}}</td>
                    <td>{{pattern.description}}</td>
                    <td>{{pattern.direction}}</td>
                    <td>{{pattern.management}}</td>
                    <td>{{pattern.isArchive ? "Да" : "Нет"}}</td>
                    <td>{{pattern.dateCreation}}</td>
                    <td>{{pattern.dateDeactivation}}</td>
                    <td>{{pattern.dateActivation}}</td>
                    <td>{{pattern.lastUpdate}}</td>
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
        name: "patternTableArchive",
        components: {MyPagination},
        data() {
            return {
                options: [{
                    value: '',
                    label: ''
                }, {
                    value: 'true',
                    label: 'Да'
                }, {
                    value: false,
                    label: 'Нет'
                }],
                value: '',
                pickerOptions: {
                    shortcuts: [{
                        text: 'Last week',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: 'Last month',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: 'Last 3 months',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        }
                    }]
                },
                url: "source/getAll?",
                patternData: "",
                pagination: {
                    pageSize: 10,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                },
                pattern: {
                    check: [],
                    key: "id",
                    sort: "",
                    id: "",
                    name: "",
                    fileCount: "",
                    sourceId: "",
                    archiveFileCount: "",
                    description: "",
                    direction: "",
                    management: "",
                    isArchive: "",
                    dateCreation: "",
                    dateCreation1: "",
                    dateCreation2: "",
                    dateDeactivation: "",
                    dateDeactivation1: "",
                    dateDeactivation2: "",
                    dateActivation: "",
                    dateActivation1: "",
                    dateActivation2: "",
                    lastUpdate: "",
                    lastUpdate1: "",
                    lastUpdate2: "",
                }
            }
        },
        methods: {
            check(id) {
                let key = this.pattern.check.indexOf(id);
                if(key !== -1){
                    this.pattern.check.splice(key,1);
                } else {
                    this.pattern.check.push(id);
                }
            },

            deArchiveSomePattern(){
                if(this.source.check.length !== 0){
                    for(let i = 0; i < this.source.check.length; i++){
                        this.deArchivePattern(this.source.check[i]);
                    }
                    this.updatePage();
                } else {
                    this.notify('Ошибка','Выберите источники которые хотите сделать активным','error');
                }
            },

            deleteSomePattern() {
                if(this.source.check.length !== 0){
                    for(let i = 0; i < this.source.check.length; i++){
                        this.deletePattern(this.source.check[i]);
                    }
                    this.updatePage();
                } else {
                    this.notify('Ошибка','Выберите источники которые хотите архивировать','error');
                }
            },

            notify(title,message,type) {
                this.$notify({
                    title: title,
                    message: message,
                    type: type
                });
            },

            deletePattern(id) {
                AXIOS.get("pattern/archive/" + id).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Шаблон был архивирован','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Шаблон не был архивирован','error');
                    }
                });
            },

            deleteOnePattern(id) {
                this.deletePattern(id);
            },

            deArchivePattern(id){
                AXIOS.get("pattern/deArchive/" + id).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Шаблон был активирован','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Шаблон не был активирован','error');
                    }
                });
            },

            deArchiveOnePattern(id){
                this.deArchivePattern(id);
            },

            sort(key){
                if(this.pattern.dateCreation !== null && this.pattern.dateCreation !== "") {
                    this.pattern.dateCreation1 = this.pattern.dateCreation[0];
                    this.pattern.dateCreation2 = this.pattern.dateCreation[1];
                } else {
                    this.pattern.dateCreation1 = "";
                    this.pattern.dateCreation2 = "";
                }

                if(this.pattern.dateDeactivation !== null && this.pattern.dateDeactivation !== "") {
                    this.pattern.dateDeactivation1 = this.pattern.dateDeactivation[0];
                    this.pattern.dateDeactivation2 = this.pattern.dateDeactivation[1];
                } else {
                    this.pattern.dateDeactivation1 = "";
                    this.pattern.dateDeactivation2 = "";
                }

                if(this.pattern.dateActivation !== null && this.pattern.dateActivation !== "") {
                    this.pattern.dateActivation1 = this.pattern.dateActivation[0];
                    this.pattern.dateActivation2 = this.pattern.dateActivation[1];
                } else {
                    this.pattern.dateActivation1 = "";
                    this.pattern.dateActivation2 = "";
                }

                if(this.pattern.lastUpdate !== null && this.pattern.lastUpdate !== "") {
                    this.pattern.lastUpdate1 = this.pattern.lastUpdate[0];
                    this.pattern.lastUpdate2 = this.pattern.lastUpdate[1];
                } else {
                    this.pattern.lastUpdate1 = "";
                    this.pattern.lastUpdate2 = "";
                }

                if(this.pattern.key === key ) {
                    switch(this.pattern.sort) {
                        case "":
                            this.pattern.sort = "asc";
                            break;
                        case "asc":
                            this.pattern.sort = "desc";
                            break;
                        case "desc":
                            this.pattern.sort = "";
                            break;
                    }
                }
                else {
                    this.pattern.key = key;
                    this.pattern.sort = "asc";
                }
                console.log(this.pattern.key);
                console.log(this.pattern.sort);
                let formData = new FormData();
                formData.append("isArchive",this.value);
                formData.append("sort",this.pattern.sort);
                formData.append("key",this.pattern.key);
                formData.append("id",this.pattern.id);
                formData.append("name",this.pattern.name);
                formData.append("direction",this.pattern.direction);
                formData.append("description",this.pattern.description);
                formData.append("management",this.pattern.management);
                formData.append("periodicity",this.pattern.periodicity);
                formData.append("sourceId",this.pattern.sourceId);
                formData.append("dateCreation1",this.pattern.dateCreation1);
                formData.append("dateCreation2",this.pattern.dateCreation2);
                formData.append("dateDeactivation1",this.pattern.dateDeactivation1);
                formData.append("dateDeactivation2",this.pattern.dateDeactivation2);
                formData.append("dateActivation1",this.pattern.dateActivation1);
                formData.append("dateActivation2",this.pattern.dateActivation2);
                formData.append("lastUpdate1",this.pattern.lastUpdate1);
                formData.append("lastUpdate2",this.pattern.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);

                AXIOS.post("/pattern/getAllSort",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    console.log(response);
                    this.pagination.totalPages = response.data.totalPages;
                    this.pagination.totalElements = response.data.totalElements;
                    this.patternData = response.data.content;
                })
            },

            onCurrentChange(value) {
                this.pagination.currentPage = value;

                if(this.pattern.dateCreation !== null && this.pattern.dateCreation !== "") {
                    this.pattern.dateCreation1 = this.pattern.dateCreation[0];
                    this.pattern.dateCreation2 = this.pattern.dateCreation[1];
                } else {
                    this.pattern.dateCreation1 = "";
                    this.pattern.dateCreation2 = "";
                }

                if(this.pattern.dateDeactivation !== null && this.pattern.dateDeactivation !== "") {
                    this.pattern.dateDeactivation1 = this.pattern.dateDeactivation[0];
                    this.pattern.dateDeactivation2 = this.pattern.dateDeactivation[1];
                } else {
                    this.pattern.dateDeactivation1 = "";
                    this.pattern.dateDeactivation2 = "";
                }

                if(this.pattern.dateActivation !== null && this.pattern.dateActivation !== "") {
                    this.pattern.dateActivation1 = this.pattern.dateActivation[0];
                    this.pattern.dateActivation2 = this.pattern.dateActivation[1];
                } else {
                    this.pattern.dateActivation1 = "";
                    this.pattern.dateActivation2 = "";
                }

                if(this.pattern.lastUpdate !== null && this.pattern.lastUpdate !== "") {
                    this.pattern.lastUpdate1 = this.pattern.lastUpdate[0];
                    this.pattern.lastUpdate2 = this.pattern.lastUpdate[1];
                } else {
                    this.pattern.lastUpdate1 = "";
                    this.pattern.lastUpdate2 = "";
                }

                let formData = new FormData();
                formData.append("isArchive",this.value);
                formData.append("sort",this.pattern.sort);
                formData.append("key",this.pattern.key);
                formData.append("id",this.pattern.id);
                formData.append("name",this.pattern.name);
                formData.append("description",this.pattern.description);
                formData.append("management",this.pattern.management);
                formData.append("periodicity",this.pattern.periodicity);
                formData.append("dateCreation1",this.pattern.dateCreation1);
                formData.append("dateCreation2",this.pattern.dateCreation2);
                formData.append("dateDeactivation1",this.pattern.dateDeactivation1);
                formData.append("dateDeactivation2",this.pattern.dateDeactivation2);
                formData.append("dateActivation1",this.pattern.dateActivation1);
                formData.append("dateActivation2",this.pattern.dateActivation2);
                formData.append("lastUpdate1",this.pattern.lastUpdate1);
                formData.append("lastUpdate2",this.pattern.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);

                AXIOS.post("/pattern/getAllSort", formData)
                    .then(response => {
                        this.patternData = response.data.content;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            },

            onSizeChange(value) {
                if(this.pattern.dateCreation !== null && this.pattern.dateCreation !== "") {
                    this.pattern.dateCreation1 = this.pattern.dateCreation[0];
                    this.pattern.dateCreation2 = this.pattern.dateCreation[1];
                } else {
                    this.pattern.dateCreation1 = "";
                    this.pattern.dateCreation2 = "";
                }

                if(this.pattern.dateDeactivation !== null && this.pattern.dateDeactivation !== "") {
                    this.pattern.dateDeactivation1 = this.pattern.dateDeactivation[0];
                    this.pattern.dateDeactivation2 = this.pattern.dateDeactivation[1];
                } else {
                    this.pattern.dateDeactivation1 = "";
                    this.pattern.dateDeactivation2 = "";
                }

                if(this.pattern.dateActivation !== null && this.pattern.dateActivation !== "") {
                    this.pattern.dateActivation1 = this.pattern.dateActivation[0];
                    this.pattern.dateActivation2 = this.pattern.dateActivation[1];
                } else {
                    this.pattern.dateActivation1 = "";
                    this.pattern.dateActivation2 = "";
                }

                if(this.pattern.lastUpdate !== null && this.pattern.lastUpdate !== "") {
                    this.pattern.lastUpdate1 = this.pattern.lastUpdate[0];
                    this.pattern.lastUpdate2 = this.pattern.lastUpdate[1];
                } else {
                    this.pattern.lastUpdate1 = "";
                    this.pattern.lastUpdate2 = "";
                }

                this.pagination.pageSize = value;
                this.pagination.currentPage = 1;

                let formData = new FormData();
                formData.append("isArchive",this.value);
                formData.append("sort",this.pattern.sort);
                formData.append("key",this.pattern.key);
                formData.append("id",this.pattern.id);
                formData.append("name",this.pattern.name);
                formData.append("description",this.pattern.description);
                formData.append("management",this.pattern.management);
                formData.append("periodicity",this.pattern.periodicity);
                formData.append("dateCreation1",this.pattern.dateCreation1);
                formData.append("dateCreation2",this.pattern.dateCreation2);
                formData.append("dateDeactivation1",this.pattern.dateDeactivation1);
                formData.append("dateDeactivation2",this.pattern.dateDeactivation2);
                formData.append("dateActivation1",this.pattern.dateActivation1);
                formData.append("dateActivation2",this.pattern.dateActivation2);
                formData.append("lastUpdate1",this.pattern.lastUpdate1);
                formData.append("lastUpdate2",this.pattern.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);

                AXIOS.post("/pattern/getAllSort", formData)
                    .then(response => {
                        this.patternData = response.data.content;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error);
                    })
            },

            updatePage() {
                if (this.pattern.dateCreation !== null && this.pattern.dateCreation !== "") {
                    this.pattern.dateCreation1 = this.pattern.dateCreation[0];
                    this.pattern.dateCreation2 = this.pattern.dateCreation[1];
                } else {
                    this.pattern.dateCreation1 = "";
                    this.pattern.dateCreation2 = "";
                }

                if (this.pattern.dateDeactivation !== null && this.pattern.dateDeactivation !== "") {
                    this.pattern.dateDeactivation1 = this.pattern.dateDeactivation[0];
                    this.pattern.dateDeactivation2 = this.pattern.dateDeactivation[1];
                } else {
                    this.pattern.dateDeactivation1 = "";
                    this.pattern.dateDeactivation2 = "";
                }

                if (this.pattern.dateActivation !== null && this.pattern.dateActivation !== "") {
                    this.pattern.dateActivation1 = this.pattern.dateActivation[0];
                    this.pattern.dateActivation2 = this.pattern.dateActivation[1];
                } else {
                    this.pattern.dateActivation1 = "";
                    this.pattern.dateActivation2 = "";
                }

                if (this.pattern.lastUpdate !== null && this.pattern.lastUpdate !== "") {
                    this.pattern.lastUpdate1 = this.pattern.lastUpdate[0];
                    this.pattern.lastUpdate2 = this.pattern.lastUpdate[1];
                } else {
                    this.pattern.lastUpdate1 = "";
                    this.pattern.lastUpdate2 = "";
                }

                let formData = new FormData();
                formData.append("isArchive",this.value);
                formData.append("sort", this.pattern.sort);
                formData.append("key", this.pattern.key);
                formData.append("id", this.pattern.id);
                formData.append("name", this.pattern.name);
                formData.append("description", this.pattern.description);
                formData.append("management", this.pattern.management);
                formData.append("periodicity", this.pattern.periodicity);
                formData.append("dateCreation1", this.pattern.dateCreation1);
                formData.append("dateCreation2", this.pattern.dateCreation2);
                formData.append("dateDeactivation1", this.pattern.dateDeactivation1);
                formData.append("dateDeactivation2", this.pattern.dateDeactivation2);
                formData.append("dateActivation1", this.pattern.dateActivation1);
                formData.append("dateActivation2", this.pattern.dateActivation2);
                formData.append("lastUpdate1", this.pattern.lastUpdate1);
                formData.append("lastUpdate2", this.pattern.lastUpdate2);
                formData.append("size", this.pagination.pageSize);
                formData.append("page", this.pagination.currentPage - 1);

                AXIOS.post("/pattern/getAllSort",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    this.pagination.totalPages = response.data.totalPages;
                    this.pagination.totalElements = response.data.totalElements;
                    this.patternData = response.data.content;
                })
            }
        },
        mounted() {
            AXIOS.get("pattern/getAll").then(response => {
                this.pagination.totalPages = response.data.totalPages;
                this.pagination.totalElements = response.data.totalElements;
                this.patternData = response.data.content;
            })
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
        padding: 10px;
        height: 50px;
    }
</style>