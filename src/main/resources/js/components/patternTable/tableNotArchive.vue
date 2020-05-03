<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <div v-if="allTable">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/patternTable/showNotArchive' }">Не архивные таблицы</el-breadcrumb-item>
            </el-breadcrumb>
            <p style="font-size: 20px">Не архивные таблицы
                <el-dropdown v-if="patternTableData.length !== 0" style="float: right" :hide-on-click="false">
                    <el-button style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394; " type="primary" icon="el-icon-s-tools">
                    </el-button>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item>
                            <el-checkbox checked="checked" @change="hiddenAll">
                                Все
                            </el-checkbox>
                        </el-dropdown-item>
                        <el-dropdown-item>
                            <el-checkbox checked="checked" id="check" @change="hidden.id = !hidden.id">
                                Номер
                            </el-checkbox>
                        </el-dropdown-item>
                        <el-dropdown-item>
                            <el-checkbox checked="checked" id="check1" @change="hidden.nameTable = !hidden.nameTable">
                                Навание таблицы
                            </el-checkbox>
                        </el-dropdown-item>
                        <el-dropdown-item>
                            <el-checkbox
                                    checked="checked"
                                    id="check2"
                                    @change="hidden.nameFile = !hidden.nameFile">
                                Название файла
                            </el-checkbox>
                        </el-dropdown-item>
                        <el-dropdown-item>
                            <el-checkbox
                                    checked="checked"
                                    id="check4"
                                    @change="hidden.dateCreation = !hidden.dateCreation">
                                Дата создания
                            </el-checkbox>
                        </el-dropdown-item>
                        <el-dropdown-item>
                            <el-checkbox checked="checked" id="check5" @change="hidden.dateDeactivation = !hidden.dateDeactivation">
                                Дата деактивации
                            </el-checkbox>
                        </el-dropdown-item>
                        <el-dropdown-item>
                            <el-checkbox checked="checked" id="check6" @change="hidden.dateActivation = !hidden.dateActivation">
                                Дата активации
                            </el-checkbox>
                        </el-dropdown-item>
                        <el-dropdown-item>
                            <el-checkbox checked="checked" id="check7" @change="hidden.lastUpdate = !hidden.lastUpdate">
                                Последнее обновление
                            </el-checkbox>
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </p>
            <div v-if="patternTableData.length !== 0" class="horizontal-scroll-wrapper  rectangles">
                <table style="display: block; overflow-x: auto;">
                    <tr>
                        <th></th>
                        <th v-if="hidden.id" @click="sort('id')">Номер</th>
                        <th v-if="hidden.nameTable" @click="sort('name_table')">Навание таблицы</th>
                        <th v-if="hidden.nameFile" @click="sort('name_file')">Навание файла</th>
                        <th v-if="hidden.dateCreation" @click="sort('date_creation')">Дата создания</th>
                        <th v-if="hidden.dateDeactivation" @click="sort('date_deactivation')">Дата деактивации</th>
                        <th v-if="hidden.dateActivation" @click="sort('date_activation')">Дата активации</th>
                        <th v-if="hidden.lastUpdate" @click="sort('last_update')">Последнее обновление</th>
                    </tr>
                    <tr>
                        <td>
                            <el-button
                                    @click="sort('')"
                                    style="background-color: #1ab394; border-color: #1ab394 "
                                    type="primary"
                                    size="mini"
                                    icon="el-icon-search"/>
                        </td>
                        <td v-if="hidden.id"><el-input placeholder="Please input" v-model="patternTable.id"></el-input></td>
                        <td v-if="hidden.nameTable"><el-input placeholder="Please input" v-model="patternTable.nameTable"></el-input></td>
                        <td v-if="hidden.nameFile"><el-input placeholder="Please input" v-model="patternTable.nameFile"></el-input></td>
                        <td v-if="hidden.dateCreation"> <div class="block">
                            <el-date-picker
                                    value-format="yyyy-MM-dd"
                                    v-model="patternTable.dateCreation"
                                    type="daterange"
                                    range-separator="To"
                                    start-placeholder="Start date"
                                    end-placeholder="End date">
                            </el-date-picker>
                        </div></td>
                        <td v-if="hidden.dateDeactivation"> <div class="block">
                            <el-date-picker
                                    value-format="yyyy-MM-dd"
                                    v-model="patternTable.dateDeactivation"
                                    type="daterange"
                                    range-separator="To"
                                    start-placeholder="Start date"
                                    end-placeholder="End date">
                            </el-date-picker>
                        </div></td>
                        <td v-if="hidden.dateActivation"> <div class="block">
                            <el-date-picker
                                    value-format="yyyy-MM-dd"
                                    v-model="patternTable.dateActivation"
                                    type="daterange"
                                    range-separator="To"
                                    start-placeholder="Start date"
                                    end-placeholder="End date">
                            </el-date-picker>
                        </div></td>
                        <td v-if="hidden.lastUpdate"> <div class="block">
                            <el-date-picker
                                    value-format="yyyy-MM-dd"
                                    v-model="patternTable.lastUpdate"
                                    type="daterange"
                                    range-separator="To"
                                    start-placeholder="Start date"
                                    end-placeholder="End date">
                            </el-date-picker>
                        </div></td>
                    </tr>
                    <tbody v-for="table in patternTableData">
                    <tr>
                        <td>
                            <el-button
                                    @click="showOneTable(table.id)"
                                    style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                    type="primary"
                                    size="mini"
                                    icon="el-icon-view"/>
                            <span v-if="table.isArchive">
                            <el-button
                                    @click="deArchiveOneTable(table.id)"
                                    style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                    type="primary"
                                    size="mini"
                                    icon="el-icon-upload2"/>
                        </span>
                            <span v-else>
                            <el-button
                                    @click="deleteOneTable(table.id)"
                                    style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "
                                    type="primary"
                                    size="mini"
                                    icon="el-icon-delete"/>
                        </span>
                        </td>
                        <!--                        <td> <el-checkbox @change="check(table.id)"></el-checkbox></td>-->
                        <td v-if="hidden.id">{{table.id}}</td>
                        <td v-if="hidden.nameTable && table.nameTable.length < 40">{{table.nameTable}}</td>
                        <td v-else-if="hidden.nameTable && table.nameTable.length >= 40">{{table.nameTable.substr(0, 40)}}<router-link :to="'/patternTable/show/' + table.id">...</router-link></td>
                        <td v-if="hidden.nameFile && table.nameFile.length < 40">{{table.nameFile}}</td>
                        <td v-else-if="hidden.nameFile && table.nameFile.length >= 40">{{table.nameFile.substr(0, 40)}}<router-link :to="'/patternTable/show/' + table.id">...</router-link></td>
                        <td v-if="hidden.dateCreation">{{table.dateCreation}}</td>
                        <td v-if="hidden.dateDeactivation">{{table.dateDeactivation}}</td>
                        <td v-if="hidden.dateActivation">{{table.dateActivation}}</td>
                        <td v-if="hidden.lastUpdate">{{table.lastUpdate}}</td>
                    </tr>
                    </tbody>
                </table>
                <my-pagination
                        :page-size="pagination.pageSize"
                        :current-page="pagination.currentPage"
                        :totalPages="pagination.totalPages"
                        :totalElements="pagination.totalElements"
                        @onCurrentChange="onCurrentChange"
                        @onSizeChange="onSizeChange"/>
            </div>
            <div v-else>
                <p style="font-size: 20px">Данных нет</p>
            </div>
        </div>
        <div v-else-if="oneTable">
            <div class="horizontal-scroll-wrapper  rectangles">
                <table style="display: block; overflow-x: auto;">
                    <tr>
                        <th v-for="pole in showOnlyOneTable.tableModel.models">{{pole.key}}</th>
                    </tr>
                    <tr v-for="value in showOnlyOneTable.values.content">
                        <td v-for="oneValue in value">{{oneValue}}</td>
                    </tr>
                </table>
                <my-pagination
                        :page-size="paginationOneTable.pageSize"
                        :current-page="paginationOneTable.currentPage"
                        :totalPages="paginationOneTable.totalPages"
                        :totalElements="paginationOneTable.totalElements"
                        @onCurrentChange="onCurrentChangeOneTable"
                        @onSizeChange="onSizeChangeOneTable"/>
                <el-button @click="showTableTab" style="margin-top: 10px; background-color: #1ab394; border-color: #1ab394; color: white;">Назад</el-button>
            </div>
        </div>
    </div>
</template>

<script>
    import router from "../../router/router";
    import {AXIOS} from "../../AXIOS/http-common";
    import MyPagination from "../general/pagination.vue";

    export default {
        name: "tableNotArchive",
        components: {MyPagination},
        data() {
            return {
                allTable: true,
                oneTable: false,
                value: '',
                patternTableData:"",
                showOnlyOneTable: "",

                options: [{
                    value: '',
                    label: 'Все'
                }, {
                    value: 'true',
                    label: 'Да'
                }, {
                    value: false,
                    label: 'Нет'
                }],

                hidden:{
                    id: true,
                    nameTable: true,
                    nameFile: true,
                    isArchive: true,
                    dateCreation: true,
                    dateDeactivation: true,
                    dateActivation: true,
                    lastUpdate: true,
                },

                pagination: {
                    pageSize: 10,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                },

                paginationOneTable: {
                    pageSize: 10,
                    currentPage: 1,
                    totalPages: 0,
                    totalElements: 0,
                },

                patternTable:{
                    check: [],
                    key: "id",
                    sort: "",
                    id:"",
                    nameTable:"",
                    nameFile:"",
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
                },
            }
        },

        methods:{
            onSizeChangeOneTable(value){
                this.paginationOneTable.pageSize = value;
                this.paginationOneTable.currentPage = 1;
                let formData = new FormData();
                formData.append("id",this.patternTableId);
                formData.append("size",this.paginationOneTable.pageSize);
                formData.append("page",this.paginationOneTable.currentPage - 1);
                AXIOS.post("tableCreator/getTable/",formData).then(response => {
                    console.log(response.data);
                    this.showOnlyOneTable = response.data;
                });
            },

            onCurrentChangeOneTable(value){
                this.paginationOneTable.currentPage = value;
                let formData = new FormData();
                formData.append("id",this.patternTableId);
                formData.append("size",this.paginationOneTable.pageSize);
                formData.append("page",this.paginationOneTable.currentPage - 1);
                AXIOS.post("tableCreator/getTable/",formData).then(response => {
                    console.log(response.data);
                    this.showOnlyOneTable = response.data;
                });
            },

            showTableTab(){
                this.oneTable = false;
                this.allTable = true;
            },

            showOneTable(id){
                router.push('show/'+ id);
            },

            notify(title,message,type) {
                this.$notify({
                    title: title,
                    message: message,
                    type: type
                });
            },

            hiddenAll(){
                document.getElementById("check").click();
                document.getElementById("check1").click();
                document.getElementById("check2").click();
                document.getElementById("check4").click();
                document.getElementById("check5").click();
                document.getElementById("check6").click();
                document.getElementById("check7").click();
            },

            deleteTable(id) {
                AXIOS.get("tableCreator/archive/" + id).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Таблица была архивирована','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Таблица не была архивирована','error');
                    }
                });
            },

            deleteOneTable(id) {
                this.deleteTable(id);
            },

            updatePage(){
                if(this.patternTable.dateCreation !== null && this.patternTable.dateCreation !== "") {
                    this.patternTable.dateCreation1 = this.patternTable.dateCreation[0];
                    this.patternTable.dateCreation2 = this.patternTable.dateCreation[1];
                } else {
                    this.patternTable.dateCreation1 = "";
                    this.patternTable.dateCreation2 = "";
                }

                if(this.patternTable.dateDeactivation !== null && this.patternTable.dateDeactivation !== "") {
                    this.patternTable.dateDeactivation1 = this.patternTable.dateDeactivation[0];
                    this.patternTable.dateDeactivation2 = this.patternTable.dateDeactivation[1];
                } else {
                    this.patternTable.dateDeactivation1 = "";
                    this.patternTable.dateDeactivation2 = "";
                }

                if(this.patternTable.dateActivation !== null && this.patternTable.dateActivation !== "") {
                    this.patternTable.dateActivation1 = this.patternTable.dateActivation[0];
                    this.patternTable.dateActivation2 = this.patternTable.dateActivation[1];
                } else {
                    this.patternTable.dateActivation1 = "";
                    this.patternTable.dateActivation2 = "";
                }

                if(this.patternTable.lastUpdate !== null && this.patternTable.lastUpdate !== "") {
                    this.patternTable.lastUpdate1 = this.patternTable.lastUpdate[0];
                    this.patternTable.lastUpdate2 = this.patternTable.lastUpdate[1];
                } else {
                    this.patternTable.lastUpdate1 = "";
                    this.patternTable.lastUpdate2 = "";
                }

                let formData = new FormData();
                formData.append("sort",this.patternTable.sort);
                formData.append("key",this.patternTable.key);
                formData.append("id",this.patternTable.id);
                formData.append("nameTable",this.patternTable.nameTable);
                formData.append("nameFile",this.patternTable.nameFile);
                formData.append("dateCreation1",this.patternTable.dateCreation1);
                formData.append("dateCreation2",this.patternTable.dateCreation2);
                formData.append("dateDeactivation1",this.patternTable.dateDeactivation1);
                formData.append("dateDeactivation2",this.patternTable.dateDeactivation2);
                formData.append("dateActivation1",this.patternTable.dateActivation1);
                formData.append("dateActivation2",this.patternTable.dateActivation2);
                formData.append("lastUpdate1",this.patternTable.lastUpdate1);
                formData.append("lastUpdate2",this.patternTable.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);
                AXIOS.post("/tableCreator/getAllNotArchiveSort/",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    this.patternTableData = response.data.content;
                })
            },

            deArchiveTable(id){
                AXIOS.get("tableCreator/deArchive/" + id).then(response => {
                    if(response.data.name !== ""){
                        this.notify('Успешно','Таблица была активирована','success');
                        this.updatePage();
                    } else {
                        this.notify('Ошибка','Таблица не была активирована','error');
                    }
                });
            },

            deArchiveOneTable(id){
                this.deArchiveTable(id);
            },

            onCurrentChange(value) {
                this.pagination.currentPage = value;

                if(this.patternTable.dateCreation !== null && this.patternTable.dateCreation !== "") {
                    this.patternTable.dateCreation1 = this.patternTable.dateCreation[0];
                    this.patternTable.dateCreation2 = this.patternTable.dateCreation[1];
                } else {
                    this.patternTable.dateCreation1 = "";
                    this.patternTable.dateCreation2 = "";
                }

                if(this.patternTable.dateDeactivation !== null && this.patternTable.dateDeactivation !== "") {
                    this.patternTable.dateDeactivation1 = this.patternTable.dateDeactivation[0];
                    this.patternTable.dateDeactivation2 = this.patternTable.dateDeactivation[1];
                } else {
                    this.patternTable.dateDeactivation1 = "";
                    this.patternTable.dateDeactivation2 = "";
                }

                if(this.patternTable.dateActivation !== null && this.patternTable.dateActivation !== "") {
                    this.patternTable.dateActivation1 = this.patternTable.dateActivation[0];
                    this.patternTable.dateActivation2 = this.patternTable.dateActivation[1];
                } else {
                    this.patternTable.dateActivation1 = "";
                    this.patternTable.dateActivation2 = "";
                }

                if(this.patternTable.lastUpdate !== null && this.patternTable.lastUpdate !== "") {
                    this.patternTable.lastUpdate1 = this.patternTable.lastUpdate[0];
                    this.patternTable.lastUpdate2 = this.patternTable.lastUpdate[1];
                } else {
                    this.patternTable.lastUpdate1 = "";
                    this.patternTable.lastUpdate2 = "";
                }

                let formData = new FormData();
                formData.append("sort",this.patternTable.sort);
                formData.append("key",this.patternTable.key);
                formData.append("id",this.patternTable.id);
                formData.append("nameTable",this.patternTable.nameTable);
                formData.append("nameFile",this.patternTable.nameFile);
                formData.append("dateCreation1",this.patternTable.dateCreation1);
                formData.append("dateCreation2",this.patternTable.dateCreation2);
                formData.append("dateDeactivation1",this.patternTable.dateDeactivation1);
                formData.append("dateDeactivation2",this.patternTable.dateDeactivation2);
                formData.append("dateActivation1",this.patternTable.dateActivation1);
                formData.append("dateActivation2",this.patternTable.dateActivation2);
                formData.append("lastUpdate1",this.patternTable.lastUpdate1);
                formData.append("lastUpdate2",this.patternTable.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);
                AXIOS.post("/tableCreator/getAllNotArchiveSort/",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    this.patternTableData = response.data.content;
                });
            },

            onSizeChange(value) {
                this.pagination.pageSize = value;
                this.pagination.currentPage = 1;
                if(this.patternTable.dateCreation !== null && this.patternTable.dateCreation !== "") {
                    this.patternTable.dateCreation1 = this.patternTable.dateCreation[0];
                    this.patternTable.dateCreation2 = this.patternTable.dateCreation[1];
                } else {
                    this.patternTable.dateCreation1 = "";
                    this.patternTable.dateCreation2 = "";
                }

                if(this.patternTable.dateDeactivation !== null && this.patternTable.dateDeactivation !== "") {
                    this.patternTable.dateDeactivation1 = this.patternTable.dateDeactivation[0];
                    this.patternTable.dateDeactivation2 = this.patternTable.dateDeactivation[1];
                } else {
                    this.patternTable.dateDeactivation1 = "";
                    this.patternTable.dateDeactivation2 = "";
                }

                if(this.patternTable.dateActivation !== null && this.patternTable.dateActivation !== "") {
                    this.patternTable.dateActivation1 = this.patternTable.dateActivation[0];
                    this.patternTable.dateActivation2 = this.patternTable.dateActivation[1];
                } else {
                    this.patternTable.dateActivation1 = "";
                    this.patternTable.dateActivation2 = "";
                }

                if(this.patternTable.lastUpdate !== null && this.patternTable.lastUpdate !== "") {
                    this.patternTable.lastUpdate1 = this.patternTable.lastUpdate[0];
                    this.patternTable.lastUpdate2 = this.patternTable.lastUpdate[1];
                } else {
                    this.patternTable.lastUpdate1 = "";
                    this.patternTable.lastUpdate2 = "";
                }

                let formData = new FormData();
                formData.append("sort",this.patternTable.sort);
                formData.append("key",this.patternTable.key);
                formData.append("id",this.patternTable.id);
                formData.append("nameTable",this.patternTable.nameTable);
                formData.append("nameFile",this.patternTable.nameFile);
                formData.append("dateCreation1",this.patternTable.dateCreation1);
                formData.append("dateCreation2",this.patternTable.dateCreation2);
                formData.append("dateDeactivation1",this.patternTable.dateDeactivation1);
                formData.append("dateDeactivation2",this.patternTable.dateDeactivation2);
                formData.append("dateActivation1",this.patternTable.dateActivation1);
                formData.append("dateActivation2",this.patternTable.dateActivation2);
                formData.append("lastUpdate1",this.patternTable.lastUpdate1);
                formData.append("lastUpdate2",this.patternTable.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);
                AXIOS.post("/tableCreator/getAllNotArchiveSort/",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    this.patternTableData = response.data.content;
                });
            },

            sort(key){
                if(this.patternTable.dateCreation !== null && this.patternTable.dateCreation !== "") {
                    this.patternTable.dateCreation1 = this.patternTable.dateCreation[0];
                    this.patternTable.dateCreation2 = this.patternTable.dateCreation[1];
                } else {
                    this.patternTable.dateCreation1 = "";
                    this.patternTable.dateCreation2 = "";
                }

                if(this.patternTable.dateDeactivation !== null && this.patternTable.dateDeactivation !== "") {
                    this.patternTable.dateDeactivation1 = this.patternTable.dateDeactivation[0];
                    this.patternTable.dateDeactivation2 = this.patternTable.dateDeactivation[1];
                } else {
                    this.patternTable.dateDeactivation1 = "";
                    this.patternTable.dateDeactivation2 = "";
                }

                if(this.patternTable.dateActivation !== null && this.patternTable.dateActivation !== "") {
                    this.patternTable.dateActivation1 = this.patternTable.dateActivation[0];
                    this.patternTable.dateActivation2 = this.patternTable.dateActivation[1];
                } else {
                    this.patternTable.dateActivation1 = "";
                    this.patternTable.dateActivation2 = "";
                }

                if(this.patternTable.lastUpdate !== null && this.patternTable.lastUpdate !== "") {
                    this.patternTable.lastUpdate1 = this.patternTable.lastUpdate[0];
                    this.patternTable.lastUpdate2 = this.patternTable.lastUpdate[1];
                } else {
                    this.patternTable.lastUpdate1 = "";
                    this.patternTable.lastUpdate2 = "";
                }

                if(this.patternTable.key === key ) {
                    switch(this.patternTable.sort) {
                        case "":
                            this.patternTable.sort = "asc";
                            break;
                        case "asc":
                            this.patternTable.sort = "desc";
                            break;
                        case "desc":
                            this.patternTable.sort = "";
                            break;
                    }
                }
                else {
                    this.patternTable.key = key;
                    this.patternTable.sort = "asc";
                }
                let formData = new FormData();
                formData.append("sort",this.patternTable.sort);
                formData.append("key",this.patternTable.key);
                formData.append("id",this.patternTable.id);
                formData.append("nameTable",this.patternTable.nameTable);
                formData.append("nameFile",this.patternTable.nameFile);
                formData.append("dateCreation1",this.patternTable.dateCreation1);
                formData.append("dateCreation2",this.patternTable.dateCreation2);
                formData.append("dateDeactivation1",this.patternTable.dateDeactivation1);
                formData.append("dateDeactivation2",this.patternTable.dateDeactivation2);
                formData.append("dateActivation1",this.patternTable.dateActivation1);
                formData.append("dateActivation2",this.patternTable.dateActivation2);
                formData.append("lastUpdate1",this.patternTable.lastUpdate1);
                formData.append("lastUpdate2",this.patternTable.lastUpdate2);
                formData.append("size",this.pagination.pageSize);
                formData.append("page",this.pagination.currentPage - 1);
                AXIOS.post("/tableCreator/getAllNotArchiveSort/",
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }).then(response => {
                    this.pagination.totalPages = response.data.totalPages;
                    this.pagination.totalElements = response.data.totalElements;
                    this.patternTableData = response.data.content;
                })
            },
        },

        mounted() {
            AXIOS.get("tableCreator/getAllNotArchive").then(response => {
                this.pagination.totalPages = response.data.totalPages;
                this.pagination.totalElements = response.data.totalElements;
                this.patternTableData = response.data.content;
                console.log(response)
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

    .el-dropdown {
        vertical-align: top;
    }
    .el-dropdown + .el-dropdown {
        margin-left: 15px;
    }
    .el-icon-arrow-down {
        font-size: 12px;
    }
</style>