<#include "header.ftl">

<div class="container">
	<div class="col-md-6 col-md-offset-3">
    <div class="logo"><img src="/goodbyeq-login/resources/images/logo.jpg" /></div>
    <div class="form-container">
      <form action="user/add" method="post">
        <div class="form-group">
          <label for="firstName">First Name</label>
          <input type="text" class="form-control" id="firstName" placeholder="Enter First Name" name="firstName">
        </div>
        <div class="form-group">
          <label for="lastName">Last Name</label>
          <input type="text" class="form-control" id="lastName" placeholder="Enter Last Name" name="lastName">
        </div>
        <div class="form-group">
          <label for="gender">Gender:</label>
          &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="gender" value="M" checked> Male &nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="gender" value="F"> Female<br>
        </div>
        <div class="form-group">
          <label for="emailID">Email:</label>
          <input type="emailID" class="form-control" id="emailID" placeholder="Enter email" name="emailID">
        </div>
        <div class="form-group">
          <label for="phoneNumber">Phone:</label>
          <input type="text" class="form-control" id="phoneNumber" placeholder="Enter phone number" name="phoneNumber" length="10">
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
        </div>  
        <div class="form-group">
          <label for="address">Address</label>
          <input type="text" class="form-control" id="address" placeholder="Enter Address" name="address">
        </div>
        <div class="form-group">
          <label for="city">City</label>
          <input type="text" class="form-control" id="city" placeholder="Enter City" name="city">
        </div>
        <div class="form-group">
          <label for="state">State</label>
          <input type="text" class="form-control" id="state" placeholder="Enter State" name="state">
        </div>
        <div class="form-group">
          <label for="zipcode">Zipcode</label>
          <input type="text" class="form-control" id="zipcode" placeholder="Enter Zipcode" name="zipcode">
        </div>
        <button type="submit" class="btn btn-lg btn-default btn-block">Sign Up</button>
      </form>
    </div> 
  </div>   
</div>

<#include "footer.ftl">