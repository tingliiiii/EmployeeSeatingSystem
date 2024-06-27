let seats = [];
let employees = [];
let selectedSeatId = null;

const loadSeats = async () => {
    try {
        const response = await fetch('http://localhost:8081/seats');
        const { state, message, data } = await response.json();
        if (state) {
            seats = data;
            renderSeats();
        }
    } catch (error) {
        console.error(error);
    }
};

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
        }
    } catch (error) {
        console.error(error);
    }

};

const renderSeats = () => {
    const seatingChart = $('#seating-chart');
    seatingChart.empty();
    const floors = [...new Set(seats.map(seat => seat.floorNo))];
    floors.forEach(floor => {
        const floorDiv = $(`<div></div>`);
        const floorSeats = seats.filter(seat => seat.floorNo === floor);
        floorSeats.sort((a, b) => a.seatNo - b.seatNo);
        floorSeats.forEach(seat => {
            const seatDiv = $(`<div class="seat ${seat.empId ? 'occupied' : 'empty'}">${floor}樓: 座位${seat.seatNo}${seat.empId ? ' [員編:' + seat.empId + ']' : ''}</div>`);
            seatDiv.click(() => selectSeat(seat, seatDiv));
            floorDiv.append(seatDiv);
        });
        seatingChart.append(floorDiv);
    });
};

const selectSeat = (seat, seatDiv) => {
    if (!seat.empId) {
        console.log(seat.floorSeatSeq);
        selectedSeatId = seat.floorSeatSeq;
        $('.seat').removeClass('selected');
        seatDiv.addClass('selected');
    }
};

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
    if (selectedEmpId && selectedSeatId) {
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
            console.error(error);
            swal.fire('Error', error, 'error');
        }
    }
};


const addEmployee = async () => {
    const empId = $('#new-emp-id').val();
    const name = $('#new-emp-name').val();
    const email = $('#new-emp-email').val();

    if (empId && name && email) {
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
                $('#new-emp-id, #new-emp-name, #new-emp-email').val('');
                loadEmployees();
            } else {
                swal.fire(message, '請稍後再試', 'error');
            }
        } catch (error) {
            console.error(error);
            swal.fire('Error', error, 'error');
        }
    } else {
        swal.fire('請填寫所有欄位', '', 'warning');
    }
};

const addSeat = async () => {
    const floorNo = $('#new-floor-no').val();
    const seatNo = $('#new-seat-no').val();

    if (floorNo && seatNo) {
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
                $('#new-floor-no, #new-seat-no').val('');
                loadSeats();
            } else {
                swal.fire(message, '請稍後再試', 'error');
            }
        } catch (error) {
            console.error(error);
            swal.fire('Error', error, 'error');
        }
    } else {
        swal.fire('請填寫所有欄位', '', 'warning');
    }
};

$(document).ready(function () {

    loadSeats();
    loadEmployees();

    $('#submit').on('click', submitSeatChange);

    $('#add-employee').on('click', (event) => {
        event.preventDefault();
        addEmployee();
    });

    $('#clear-employee-form').on('click', (event) => {
        event.preventDefault();
        $('#new-emp-id, #new-emp-name, #new-emp-email').val('');
    });

    $('#add-seat').on('click', (event) => {
        event.preventDefault();
        addSeat();
    });

    $('#clear-seat-form').on('click', (event) => {
        event.preventDefault();
        $('#new-floor-no, #new-seat-no').val('');
    });

})