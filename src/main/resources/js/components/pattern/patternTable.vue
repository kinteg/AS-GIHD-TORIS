<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Архинвые источники
            <el-button class="trt" @click="deleteSomeSource"  style="float: right; margin-left: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-delete"></el-button>
            <el-button @click="deArchiveSomeSource"  style="float: right; margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary"  icon="el-icon-upload2"></el-button>
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
                    <td><el-input placeholder="Please input" v-model="pattern.isArchive"></el-input></td>
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
                <tr >
                    <td>
                         <span v-if="pattern.isArchive">
                            <el-button @click="deArchiveOneSource(source.id)"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-upload2"></el-button>
                        </span>
                        <span v-else>
                            <el-button @click="deleteOneSource(source.id)"  style="margin-bottom: 10px; background-color: #1ab394; border-color: #1ab394 "  type="primary" size="mini" icon="el-icon-delete"></el-button>
                        </span>
                    </td>
                    <td> <el-checkbox @change="check(pattern.id)"></el-checkbox></td>
                    <td>{{pattern.id}}</td>
                    <td>{{pattern.name}}</td>
                    <td>{{pattern.description}}</td>
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
        name: "patternTable",
        components: {MyPagination},
        data() {
            return {
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