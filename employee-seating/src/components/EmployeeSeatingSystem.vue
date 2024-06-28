<template>
  <div class="container mt-5">
    <h1 class="text-center">Employee Seating System</h1>
    <table class="table">
      <tr>
        <td colspan="3" class="text-center">
          <div id="seating-chart" class="m-4">
            <div v-for="seat in seats" :key="seat.floorSeatSeq" class="seat" :class="{ occupied: seat.empId, empty: !seat.empId, selected: selectedSeatId === seat.floorSeatSeq }" @click="selectSeat(seat)">
              <div>{{ seat.floorNo }}樓: 座位{{ seat.seatNo }} <span v-if="seat.empId">[員編: {{ seat.empId }}]</span></div>
            </div>
          </div>
        </td>
      </tr>
      <tr>
        <td>
          <div class="m-4">
            <h2 class="m-4 text-center">調整員工座位</h2>
            <label for="employee" class="form-label">員工</label>
            <select v-model="selectedEmpId" id="employee" class="form-select">
              <option value="" selected disabled>請選擇員工</option>
              <option v-for="employee in employees" :key="employee.empId" :value="employee.empId">{{ employee.name }} ({{ employee.empId }})</option>
            </select>
            <div class="text-center mt-3">
              <button @click="submitSeatChange" class="btn btn-primary mt-3">調整座位</button>
              <button @click="resetSelection" class="btn btn-outline-primary mt-3">取消</button>
            </div>
          </div>
        </td>
        <td>
          <div class="m-4">
            <h2 class="m-4 text-center">新增員工</h2>
            <form @submit.prevent="addEmployee">
              <label for="new-emp-id" class="form-label">員工編號</label>
              <input v-model="newEmployee.id" type="number" id="new-emp-id" class="form-control" pattern="[0-9]{5}" placeholder="請輸入五位數字" required>
              <label for="new-emp-name" class="form-label mt-2">姓名</label>
              <input v-model="newEmployee.name" type="text" id="new-emp-name" class="form-control" placeholder="請輸入姓名" required>
              <label for="new-emp-email" class="form-label mt-2">email</label>
              <input v-model="newEmployee.email" type="email" id="new-emp-email" class="form-control" placeholder="請輸入email" required>
              <div class="text-center mt-3">
                <button type="submit" class="btn btn-primary m-1">新增員工</button>
                <button @click="clearEmployeeForm" type="button" class="btn btn-outline-primary m-1">取消</button>
              </div>
            </form>
          </div>
        </td>
        <td>
          <div class="m-4">
            <h2 class="m-4 text-center">新增座位</h2>
            <form @submit.prevent="addSeat">
              <label for="new-floor-no" class="form-label">樓層</label>
              <input v-model="newSeat.floorNo" type="number" id="new-floor-no" class="form-control" placeholder="請輸入樓層數">
              <label for="new-seat-no" class="form-label mt-2">座位號碼</label>
              <input v-model="newSeat.seatNo" type="number" id="new-seat-no" class="form-control" placeholder="請輸入座位號碼">
              <div class="text-center mt-3">
                <button type="submit" class="btn btn-primary m-1">新增座位</button>
                <button @click="clearSeatForm" type="button" class="btn btn-outline-primary m-1">取消</button>
              </div>
            </form>
          </div>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';

export default {
  name: 'EmployeeSeatingSystem',
  data() {
    return {
      seats: [],
      employees: [],
      selectedSeatId: null,
      selectedEmpId: '',
      newEmployee: {
        id: '',
        name: '',
        email: ''
      },
      newSeat: {
        floorNo: '',
        seatNo: ''
      }
    };
  },
  created() {
    this.loadSeats();
    this.loadEmployees();
  },
  methods: {
    async loadSeats() {
      try {
        const response = await axios.get('http://localhost:8081/seats');
        const { state, message, data } = response.data;
        if (state) {
          this.seats = data;
        } else {
          Swal.fire(message, '請稍後再試', 'error');
        }
      } catch (error) {
        console.error('Error loading seats:', error);
        Swal.fire('下載座位資訊發生錯誤', error.message, 'error');
      }
    },
    async loadEmployees() {
      try {
        const response = await axios.get('http://localhost:8081/employees');
        const { state, message, data } = response.data;
        if (state) {
          this.employees = data;
        } else {
          Swal.fire(message, '請稍後再試', 'error');
        }
      } catch (error) {
        console.error('Error loading employees:', error);
        Swal.fire('下載員工資訊發生錯誤', error.message, 'error');
      }
    },
    selectSeat(seat) {
      if (!seat.empId) {
        this.selectedSeatId = seat.floorSeatSeq;
      }
    },
    async submitSeatChange() {
      if (!this.selectedSeatId) {
        Swal.fire('請選擇座位', '', 'warning');
        return;
      }
      if (!this.selectedEmpId) {
        Swal.fire('請選擇員工', '', 'warning');
        return;
      }
      try {
        const response = await axios.put('http://localhost:8081/seats', {
          empId: this.selectedEmpId,
          floorSeatSeq: this.selectedSeatId
        });
        const { state, message } = response.data;
        if (state) {
          Swal.fire(message, '', 'success');
          this.loadSeats();
          this.selectedSeatId = null;
          this.selectedEmpId = '';
        } else {
          Swal.fire(message, '請稍後再試', 'error');
        }
      } catch (error) {
        console.error('Error submitting seat change:', error);
        Swal.fire('變更座位發生錯誤', error.message, 'error');
      }
    },
    async addEmployee() {
      const { id, name, email } = this.newEmployee;
      if (!id || !name || !email) {
        Swal.fire('請填寫所有欄位', '', 'warning');
        return;
      }
      try {
        const response = await axios.post('http://localhost:8081/employees', {
          empId: id,
          name,
          email
        });
        const { state, message } = response.data;
        if (state) {
          Swal.fire(message, '', 'success');
          this.loadEmployees();
          this.clearEmployeeForm();
        } else {
          Swal.fire(message, '請稍後再試', 'error');
        }
      } catch (error) {
        console.error('Error adding employee:', error);
        Swal.fire('新增員工發生錯誤', error.message, 'error');
      }
    },
    async addSeat() {
      const { floorNo, seatNo } = this.newSeat;
      if (!floorNo || !seatNo) {
        Swal.fire('請填寫所有欄位', '', 'warning');
        return;
      }
      try {
        const response = await axios.post('http://localhost:8081/seats', {
          floorNo,
          seatNo
        });
        const { state, message } = response.data;
        if (state) {
          Swal.fire(message, '', 'success');
          this.loadSeats();
          this.clearSeatForm();
        } else {
          Swal.fire(message, '請稍後再試', 'error');
        }
      } catch (error) {
        console.error('Error adding seat:', error);
        Swal.fire('新增座位失敗', error.message, 'error');
      }
    },
    clearEmployeeForm() {
      this.newEmployee = { id: '', name: '', email: '' };
    },
    clearSeatForm() {
      this.newSeat = { floorNo: '', seatNo: '' };
    },
    resetSelection() {
      this.selectedEmpId = '';
      this.selectedSeatId = null;
    }
  }
};
</script>

<style scoped>
.seat {
  display: inline-block;
  margin: 5px;
  padding: 10px;
  cursor: pointer;
}
.seat.occupied {
  background-color: #f2f2f2;
}
.seat.empty {
  background-color: #e6e6e6;
}
.seat.selected {
  background-color: #4caf50;
  color: white;
}
</style>
