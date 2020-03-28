<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Добавление источника</p>
        <hr>
        <div>
            <el-form :inline="true" :label-position="labelPosition" label-width="100px">
                <el-form-item prop="name" label="Поставщик данных">
                    {{source.name}}

                </el-form-item>
                <hr><el-form-item prop="longName" label="Полное наименование набора">
                    {{source.longName}}
                </el-form-item>
                <hr><el-form-item prop="shortName" label="Краткое наименование набора ">
                    {{source.shortName}}
                </el-form-item>
                <hr><el-form-item prop="description" label="Описание ">
                    {{source.description}}
                </el-form-item>
                <hr><el-form-item prop="addDescription" label="Дополнительное описание">
                    {{source.addDescription}}
                </el-form-item>
                <hr><el-form-item prop="scope" label="Сфера (направление)">
                    {{source.scope}}
                </el-form-item>
                <hr><el-form-item prop="periodicity" label="Периодичность актуализации">
                    {{source.periodicity}}
                </el-form-item>
                <hr><el-form-item prop="renewalPeriod" label="Срок обновления набора данных">
                    {{source.renewalPeriod}}
                </el-form-item>
                <hr><el-form-item prop="type" label="Вид набора">
                    {{source.type}}
                </el-form-item>
                <hr><el-form-item prop="tags" label="Ключевые слова (теги)">
                    {{source.tags}}
                </el-form-item>
                <hr><el-form-item prop="providerLink" label="Информационная система - источник данных ">
                    {{source.providerLink}}
                </el-form-item>
                <hr><el-form-item prop="dataSource" label="Ссылка на данные на сайте поставщика">
                    {{source.dataSource}}
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";

    export default {
        name: "sourceView",
        data() {
            return {
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