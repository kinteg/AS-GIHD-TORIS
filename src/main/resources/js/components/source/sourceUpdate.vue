<template>
    <div style="background-color: white; padding: 30px;  border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);" >
        <p style="font-size: 20px">Обновление</p>
        <hr>
        <div>
            <el-form :label-position="labelPosition" label-width="100px" :model="source">
                <el-form-item label="Name">
                    <el-input v-model="source.pole1"></el-input>
                </el-form-item>
                <el-form-item label="Activity zone">
                    <el-input v-model="source.pole2"></el-input>
                </el-form-item>
                <el-form-item label="Activity form">
                    <el-input v-model="source.pole3"></el-input>
                </el-form-item>
                <el-form-item label="Activity form">
                    <el-input v-model="source.pole4"></el-input>
                </el-form-item>
                <el-form-item>
                    <button style="margin: 20px;">Сохранить</button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "sourceUpdate",
        data(){
            return{
                labelPosition:"top",
                source:{
                    pole1:"",
                    pole2:"",
                    pole3:"",
                    pole4:""
                }
            }
        }
    }
</script>

<style scoped>

</style>