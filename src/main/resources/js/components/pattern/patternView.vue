<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Просмотр шаблона</p>
        <el-row :gutter="20">
            <el-col :span="8">
                <div>
                    <el-form :label-position="labelPosition" label-width="100px" :model="pattern">
                        <el-form-item class="label" label="Название:">
                            {{pattern.name}}
                        </el-form-item>
                        <el-form-item class="label" label="Описание:">
                            {{pattern.description}}
                        </el-form-item>
                        <el-form-item class="label" label="Сфера (направление):">
                            {{pattern.direction}}
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
            <el-col :span="8">
                <div>
                    <el-form :label-position="labelPosition" label-width="100px" :model="pattern">
                        <el-form-item class="label" label="Ответственный за ведение:">
                            {{pattern.management}}
                        </el-form-item>
                        <el-form-item class="label" label="Архивность:">
                            {{pattern.isArchive ? "Да" : "Нет"}}
                        </el-form-item>
                        <el-form-item class="label" label="Дата создания:">
                            {{pattern.dateCreation}}
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
            <el-col :span="8">
                <div>
                    <el-form :label-position="labelPosition" label-width="100px" :model="pattern">
                        <el-form-item class="label" label="Дата архивации:">
                            {{pattern.dateDeactivation}}
                        </el-form-item>
                        <el-form-item class="label" label="Дата активации:">
                            {{pattern.dateActivation}}
                        </el-form-item>
                        <el-form-item class="label" label="Последнее обновление:">
                            {{pattern.lastUpdate}}
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";

    export default {
        name: "patternView",
        props: ['patternId'],
        data() {
            return {
                labelPosition: "top",
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
                },
            }
        },
        mounted() {
            AXIOS.get("pattern/" + this.patternId).then(response => {
                this.pattern = response.data;
            })
        }
    }
</script>

<style scoped>
    .label{
        word-break: break-all;
    }
</style>