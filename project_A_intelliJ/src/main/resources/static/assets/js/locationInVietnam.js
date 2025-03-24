// Fetch provinces
fetch("https://esgoo.net/api-tinhthanh/1/0.htm")
    .then(response => response.json())
    .then(data => {
        let provincesSelect = document.getElementById('provinces');
        provincesSelect.innerHTML = `<option value=''>Select</option>`;

        data.data.forEach(value => {
            provincesSelect.innerHTML += `<option value="${value.name}" data-id="${value.id}">${value.name}</option>`;
        });

        // Check if niceSelect exists before updating
        if ($.fn.niceSelect) {
            $(provincesSelect).niceSelect();
            $(provincesSelect).niceSelect('update');
        }
    })
    .catch(error => console.error("Error fetching provinces:", error));

// Fetch districts based on selected province ID
function fetchDistricts(provinceID) {
    fetch(`https://esgoo.net/api-tinhthanh/2/${provinceID}.htm`)
        .then(response => response.json())
        .then(data => {
            let districtsSelect = document.getElementById('districts');
            districtsSelect.innerHTML = `<option value=''>Select</option>`;

            data.data.forEach(value => {
                districtsSelect.innerHTML += `<option value="${value.name}" data-id="${value.id}">${value.name}</option>`;
            });

            if ($.fn.niceSelect) {
                $(districtsSelect).niceSelect('update');
            }
        })
        .catch(error => console.error("Error fetching districts:", error));
}

// Fetch wards based on selected district ID
function fetchWards(districtID) {
    fetch(`https://esgoo.net/api-tinhthanh/3/${districtID}.htm`)
        .then(response => response.json())
        .then(data => {
            let wardsSelect = document.getElementById('wards');
            wardsSelect.innerHTML = `<option value=''>Select</option>`;

            data.data.forEach(value => {
                wardsSelect.innerHTML += `<option value="${value.name}" data-id="${value.id}">${value.name}</option>`;
            });

            if ($.fn.niceSelect) {
                $(wardsSelect).niceSelect('update');
            }
        })
        .catch(error => console.error("Error fetching wards:", error));
}

// Handle province selection
function getProvinces(event) {
    let selectedOption = event.target.options[event.target.selectedIndex];
    let provinceId = selectedOption.getAttribute("data-id");

    // Reset districts and wards when changing province
    document.getElementById('districts').innerHTML = `<option value=''>Select</option>`;
    document.getElementById('wards').innerHTML = `<option value=''>Select</option>`;

    if (provinceId) {
        fetchDistricts(provinceId);
    }

    updateAddress();
}

// Handle district selection
function getDistricts(event) {
    let selectedOption = event.target.options[event.target.selectedIndex];
    let districtId = selectedOption.getAttribute("data-id");

    // Reset wards when changing district
    document.getElementById('wards').innerHTML = `<option value=''>Select</option>`;

    if (districtId) {
        fetchWards(districtId);
    }

    updateAddress();
}

// Update hidden address field
function updateAddress() {
    let provinceSelect = document.getElementById('provinces');
    let districtSelect = document.getElementById('districts');
    let wardSelect = document.getElementById('wards');
    let detailInput = document.getElementById('detailAddr');
    let fullAddressInput = document.getElementById('fullAddress'); // Might be missing on some pages

    if (!provinceSelect || !districtSelect || !wardSelect) {
        console.error("One or more select elements are missing.");
        return;
    }

    let province = provinceSelect.value;
    let district = districtSelect.value;
    let ward = wardSelect.value;
    let detail = detailInput ? detailInput.value : '';

    let fullAddress = [detail, ward, district, province].filter(Boolean).join(", ");

    if (fullAddressInput) {
        fullAddressInput.value = fullAddress;
    } else {
        console.warn("fullAddress input field not found. Skipping update.");
    }

    console.log("Updated address:", fullAddress);

    // Close nice-select dropdown
    closeNiceSelect("#wards");
}

// 🔹 Add this function to force-close the dropdown
function closeNiceSelect(selector) {
    let element = document.querySelector(selector);
    if (element) {
        $(element).niceSelect('update'); // Refresh the select
        $(element).blur(); // Remove focus
        console.log("NiceSelect dropdown closed for", selector);
    } else {
        console.warn(`Element ${selector} not found.`);
    }
}


