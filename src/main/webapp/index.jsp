<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="haed.jsp" />
    </head>
    <body>
        <jsp:include page="haeder.jsp" />
        <div class="outbox"><form action="search">
            <fieldset><legend>نوع ملک</legend>
                <input type="radio" name="buildingType" value="آپارتمان" checked>آپارتمان</input>
                <input type="radio" name="buildingType" value="ویلایی">ویلایی</input>
            </fieldset>
            <fieldset><legend>نوع قرارداد</legend>
                <input type="radio" name="dealType" value="0" checked>فروش</input>
                <input type="radio" name="dealType" value="1">رهن و اجاره</input>
            </fieldset>
            <input type="text" name="minArea" placeholder="حداقل متراژ">
            <input type="text" name="maxPrice" placeholder="حداکثر قیمت">
            <button type="submit">جست‌وجو</button>
        </form></div>
        <div class="outbox"><form action="addHouse">
            <fieldset><legend>نوع ملک</legend>
                <input type="radio" name="buildingType" value="آپارتمان" checked>آپارتمان</input>
                <input type="radio" name="buildingType" value="ویلایی">ویلایی</input>
            </fieldset>
            <fieldset><legend>نوع قرارداد</legend>
                <input type="radio" name="dealType" value="0" checked>فروش</input>
                <input type="radio" name="dealType" value="1">رهن و اجاره</input>
            </fieldset>
            <input type="text" name="address" placeholder="آدرس">
            <input type="text" name="description" placeholder="توضیحات">
            <input type="text" name="area" placeholder="متراژ">
            <input type="text" name="price" placeholder="قیمت فروش / اجاره">
            <input type="text" name="phone" placeholder="تلفن">
            <button type="submit">اضافه‌کردن خانهٔ جدید</button>
        </form></div>
        <div class="outbox"><form action="javascript: void(0);">
            <input type="text" name="balance" placeholder="اعتبار">
            <button type="submit">افزایش اعتبار</button>
        </form></div>
    </body>
</html>
