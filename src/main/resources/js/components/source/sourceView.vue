<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Просмотр источника</p>
        <div>
            <el-tabs v-model="activeName">
                <el-tab-pane label="Источник" name="sourceInfo">
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <div>
                                <el-form :label-position="labelPosition" label-width="100px" :model="source">
                                    <el-form-item label="Поставщик данных:">
                                        {{source.name}}
                                    </el-form-item>
                                    <el-form-item label="Полное наименование набора:">
                                        {{source.longName}}
                                    </el-form-item>
                                    <el-form-item label="Краткое наименование набора:">
                                        {{source.shortName}}
                                    </el-form-item>
                                    <el-form-item label="Описание:">
                                        {{source.description}}
                                    </el-form-item>
                                    <el-form-item label="Дополнительное описание:">
                                        {{source.addDescription}}
                                    </el-form-item>
                                    <el-form-item label="Сфера (направление):">
                                        {{source.scope}}
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div>
                                <el-form :label-position="labelPosition" label-width="100px" :model="source">
                                    <el-form-item label="Периодичность актуализации:">
                                        {{source.periodicity}}
                                    </el-form-item>
                                    <el-form-item label="Срок обновления набора данных:">
                                        {{source.renewalPeriod}}
                                    </el-form-item>
                                    <el-form-item label="Вид набора:">
                                        {{source.type}}
                                    </el-form-item>
                                    <el-form-item label="Ключевые слова (теги):">
                                        {{source.tags}}
                                    </el-form-item>
                                    <el-form-item label="Источник данных:">
                                        {{source.providerLink}}
                                    </el-form-item>
                                    <el-form-item label="Ссылка на данные на сайте поставщика:">
                                        {{source.dataSource}}
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                    </el-row>
                </el-tab-pane>
                <el-tab-pane label="Шаблоны" name="patternInfo">
                    <span v-if="hiddenTable"  style="background-color: white;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
                       <pattern-table style="padding: 0 5px 0 0; box-shadow: none "/>
                    </span>
                    <div v-else>
                        <pattern-view :pattern-id="this.patternId" />
                        <el-button @click="backView" style="background-color: #1ab394; border-color: #1ab394; color: white;">Назад</el-button>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";
    import PatternView from "../pattern/patternView.vue";
    import PatternTable from "../pattern/patternTable.vue";

    export default {
        name: "sourceView",
        components: {PatternTable, PatternView},
        data() {
            return {
                patternData:[],
                patternId: "",
                hiddenTable: true,
                hiddenView: false,
                hiddenUpdate: false,
                activeName: "sourceInfo",
                labelPosition: "top",
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
                    tags:"",
                    providerLink:"",
                    dataSource:"",
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
                    dateDeactivation: "",
                    dateActivation: "",
                    lastUpdate: "",
                }
            }
        },

        methods:{
            patternView(id) {
                this.hiddenTable = false;
                this.patternId = id;
            },

            backView(){
                this.hiddenTable = true;
                this.patternId = "";
            }
        },
        mounted() {
            AXIOS.get("source/" + this.$route.params.id).then(response => {
                console.log(response);
                this.source = response.data;
            });

            AXIOS.get("pattern/getAll/" + this.$route.params.id).then(response => {
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