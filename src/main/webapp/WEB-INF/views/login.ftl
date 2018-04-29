<#include "header.ftl">

<div class="container">
	<div class="col-md-6 col-md-offset-3">
    <div class="logo"><img src="/goodbyeq-login/resources/images/logo.jpg" /></div>
    <div class="form-container">
  <form action="">
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" class="form-control" id="email" placeholder="Enter email" name="email">
    </div>
    <div class="form-group viewholder">
      <label for="pwd">Password:</label>
      <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pwd">
      <div class="viewpwd">      
      <span class="glyphicon glyphicon-eye-open"></span>
      </div>
    </div>
    <button type="button" class="btn btn-lg btn-default btn-block">Login</button>
    
  </form>
  <div class="forgot">
  <a href="#" >Forgot your password?</a>
  </div>
  </div>
  <!-- <button type="button" class="btn btn-lg btn-warning btn-block">Sign Up</button> -->
  <input type="button" class="btn btn-lg btn-warning btn-block" onclick="location.href='/goodbyeq-login/signup';" value="Sign Up" />
  </div>
</div>

<#include "footer.ftl">