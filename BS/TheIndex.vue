<template>
  <div class="index-container">
    <el-container>
      <el-header>
        <div>
          <span>个人通讯录系统</span>
        </div>
        <div>
          <el-menu default-active="1" class="el-menu-demo" mode="horizontal" background-color="#B3C0D1">
            <el-menu-item index="1"><el-button type="primary" @click="showAddContact">添加联系人</el-button></el-menu-item>
          </el-menu>
        </div>
      </el-header>
      <el-main>
        <el-table :data="contacts" style="width: 100%">
          <el-table-column prop="name" label="姓名" width="180"></el-table-column>
          <el-table-column prop="address" label="住址" width="180"></el-table-column>
          <el-table-column prop="phone" label="电话" width="180"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" @click="showEditContact(scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="deleteContact(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>

    <!-- 添加联系人对话框 -->
    <el-dialog title="添加联系人" :visible.sync="addContactVisible" width="30%">
      <el-form :model="newContact">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="newContact.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="住址" :label-width="formLabelWidth">
          <el-input v-model="newContact.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="newContact.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addContactVisible = false">取 消</el-button>
        <el-button type="primary" @click="addContact">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑联系人对话框 -->
    <el-dialog title="编辑联系人" :visible.sync="editContactVisible" width="30%">
      <el-form :model="currentContact">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="currentContact.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="住址" :label-width="formLabelWidth">
          <el-input v-model="currentContact.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="currentContact.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editContactVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateContact">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      contacts: [],
      newContact: { name: '', address: '', phone: '' },
      currentContact: { name: '', address: '', phone: '' },
      addContactVisible: false,
      editContactVisible: false,
      formLabelWidth: '120px'
    };
  },
  methods: {
    showAddContact() {
      this.addContactVisible = true;
    },
    addContact() {
      this.contacts.push({ ...this.newContact });
      this.addContactVisible = false;
    },
    showEditContact(row) {
      this.currentContact = { ...row };
      this.editContactVisible = true;
    },
    updateContact() {
      const index = this.contacts.findIndex(item => item.name === this.currentContact.name);
      if (index !== -1) {
        this.contacts.splice(index, 1, { ...this.currentContact });
        this.editContactVisible = false;
      }
    },
    deleteContact(row) {
      const index = this.contacts.findIndex(item => item.name === row.name);
      if (index !== -1) {
        this.contacts.splice(index, 1);
      }
    }
  }
};
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-main {
  padding: 20px;
}

.el-aside {
  background-color: #545c64;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-container {
  height: 100vh;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>