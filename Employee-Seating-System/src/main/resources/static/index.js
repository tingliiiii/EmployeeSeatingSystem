let seats = [];
let employees = [];
let selectedSeatId = null;

// 下載座位資訊
const loadSeats = async () => {
    try {
        const response = await fetch('http://localhost:8081/seats');
        const { state, message, data } = await response.json();
        if (state) {
            seats = data;
            renderSeats();
        } else {
            swal.fire(message, '請稍後再試', 'error');
        }
    } catch (error) {
        console.error('Error loading seats:', error);
        swal.fire('下載座位資訊發生錯誤', error, 'error');
    }
};

// 下載員工資訊
const loadEmployees = async () => {

    try {
        const response = await fetch('http://localhost:8081/employees');
        const { state, message, data } = await response.json();
        if (state) {
            employees = data;
            const employeeSelect = $('#employee');
            employeeSelect.empty();
            employeeSelect.append('<option value="" selected disabled>請選擇員工</option>');
            employees.forEach(employee => {
                employeeSelect.append(`<option value="${employee.empId}">${employee.name} (${employee.empId})</option>`);
            });
        } else {
            swal.fire(message, '請稍後再試', 'error');
        }
    } catch (error) {
        console.error('Error loading employees:', error);
        swal.fire('下載員工資訊發生錯誤', error, 'error');

    }

};

// 渲染座位
const renderSeats = () => {
    const seatingChart = $('#seating-chart');
    seatingChart.empty();
    // 取得所有樓層
    const floors = [...new Set(seats.map(seat => seat.floorNo))];
    floors.forEach(floor => {
        const floorDiv = $(`<div></div>`);
        const floorSeats = seats.filter(seat => seat.floorNo === floor);
        // 按照 seatNo 排序
        floorSeats.sort((a, b) => a.seatNo - b.seatNo);
        floorSeats.forEach(seat => {
            const seatDiv = $(`<div class="seat ${seat.empId ? 'occupied' : 'empty'}">${floor}樓: 座位${seat.seatNo}${seat.empId ? ' [員編:' + seat.empId + ']' : ''}</div>`);
            seatDiv.click(() => selectSeat(seat, seatDiv));
            floorDiv.append(seatDiv);
        });
        seatingChart.append(floorDiv);
    });
};

// 選擇座位時變成綠色
const selectSeat = (seat, seatDiv) => {
    if (!seat.empId) {
        // console.log(seat.floorSeatSeq);
        selectedSeatId = seat.floorSeatSeq;
        $('.seat').removeClass('selected');
        seatDiv.addClass('selected');
    }
};

// 執行座位變更
const submitSeatChange = async () => {

    if (!selectedSeatId) {
        swal.fire('請選擇座位', '', 'warning');
        return;
    }
    const selectedEmpId = $('#employee').val();
    if (!selectedEmpId) {
        swal.fire('請選擇員工', '', 'warning');
        return;
    }
    console.log(selectedEmpId, selectedSeatId);
    try {
        const response = await fetch('http://localhost:8081/seats', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ empId: selectedEmpId, floorSeatSeq: selectedSeatId })
        });
        const { state, message, data } = await response.json();
        console.log(state, message, data);
        if (state) {
            swal.fire(message, '', 'success');
            loadSeats();
        } else {
            swal.fire(message, '請稍後再試', 'error');
        }
    } catch (error) {
        console.error('Error submitting seat change:', error);
        swal.fire('變更座位發生錯誤', error, 'error');
    }

};

// 新增員工
const addEmployee = async () => {
    const empId = $('#new-emp-id').val();
    const name = $('#new-emp-name').val();
    const email = $('#new-emp-email').val();

    if (!empId || !name || !email) {
        swal.fire('請填寫所有欄位', '', 'warning');
        return;
    }

    try {
        const response = await fetch('http://localhost:8081/employees', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ empId, name, email })
        });
        const { state, message, data } = await response.json();

        if (state) {
            swal.fire(message, '', 'success');
            clearEmployeeForm();
            loadEmployees();
        } else {
            swal.fire(message, '請稍後再試', 'error');
        }
    } catch (error) {
        console.error('Error adding employee:', error);
        swal.fire('新增員工發生錯誤', error, 'error');
    }
};

// 新增座位
const addSeat = async () => {
    const floorNo = $('#new-floor-no').val();
    const seatNo = $('#new-seat-no').val();

    if (!floorNo || !seatNo) {
        swal.fire('請填寫所有欄位', '', 'warning');
        return;
    }

    try {
        const response = await fetch('http://localhost:8081/seats', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ floorNo, seatNo })
        });
        const { state, message, data } = await response.json();

        if (state) {
            swal.fire(message, '', 'success');
            clearSeatForm();
            loadSeats();
        } else {
            swal.fire(message, '請稍後再試', 'error');
        }
    } catch (error) {
        console.error('Error adding seat:', error);
        swal.fire('新增座位失敗', error, 'error');
    }

};

// 清空新增員工表單
const clearEmployeeForm = () => {
    $('#new-emp-id, #new-emp-name, #new-emp-email').val('');
};

// 清空新增座位表單
const clearSeatForm = () => {
    $('#new-floor-no, #new-seat-no').val('');
};

$(document).ready(function () {

    loadSeats();
    loadEmployees();

    $('#submit').on('click', submitSeatChange);

    $('#reset').on('click', () => {
        $('#employee').val('');
        $('.seat').removeClass('selected');
    });

    $('#add-employee').on('click', (event) => {
        event.preventDefault();
        addEmployee();
    });

    $('#clear-employee-form').on('click', (event) => {
        event.preventDefault();
        clearEmployeeForm();
    });

    $('#add-seat').on('click', (event) => {
        event.preventDefault();
        addSeat();
    });

    $('#clear-seat-form').on('click', (event) => {
        event.preventDefault();
        clearSeatForm();
    });

})