<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Просмотр шаблона</p>
        <el-row :gutter="20">
            <el-col :span="8">
                <div>
                    <el-form :label-position="labelPosition" label-width="100px" :model="pattern">
                        <el-form-item label="Название:">
                            {{pattern.name}}
                        </el-form-item>
                        <el-form-item label="Описание:">
                            {{pattern.description}}
                        </el-form-item>
                        <el-form-item label="Сфера (направление):">
                            {{pattern.direction}}
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
            <el-col :span="8">
                <div>
                    <el-form :label-position="labelPosition" label-width="100px" :model="pattern">
                        <el-form-item label="Ответственный за ведение:">
                            {{pattern.management}}
                        </el-form-item>
                        <el-form-item label="Архивность:">
                            {{pattern.isArchive ? "Да" : "Нет"}}
                        </el-form-item>
                        <el-form-item label="Дата создания:">
                            {{pattern.dateCreation}}
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
            <el-col :span="8">
                <div>
                    <el-form :label-position="labelPosition" label-width="100px" :model="pattern">
                        <el-form-item label="Дата архивации:">
                            {{pattern.dateDeactivation}}
                        </el-form-item>
                        <el-form-item label="Дата активации:">
                            {{pattern.dateActivation}}
                        </el-form-item>
                        <el-form-item label="Последнее обновление:">
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
                console.log(response.data);
                this.pattern = response.data;
            })
        }
    }
</script>

<style scoped>

</style>