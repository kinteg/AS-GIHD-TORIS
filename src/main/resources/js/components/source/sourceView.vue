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
                <el-tab-pane label="Шаблоны" name="patternInfo">Шаблоны</el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";

    export default {
        name: "sourceView",
        data() {
            return {
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
            }
        },
        mounted() {
            AXIOS.get("source/" + this.$route.params.id).then(response => {
                console.log(response);
                this.source = response.data;
            })
        }
    }
</script>

<style scoped>

</style>