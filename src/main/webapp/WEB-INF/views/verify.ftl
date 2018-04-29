<#include "header.ftl">

<body>
<div class="container">
	<div class="col-md-6 col-md-offset-3">
    <div class="logo"><img src="/goodbyeq-login/resources/images/logo.jpg" /></div>
    <div class="form-container">
    <form action="verify" method="post">
        <div class="form-group">
          <label class = "verifyTxt" for="verifyCode" style="">Verify that you have this email. </label>
          <label class = "verifyTxt" for="verifyCode" style="">Enter the verification code sent to </label>
          <label class = "verifyTxt" for="verifyCode" style="">a**********ey@gmail.com.</label> 
            <br> </br>  
          <input class = "verifyCode" type="text" class="form-control" id="verifyCode" placeholder="--------" name="verifyCode"  maxlength="8" autofocus = "true" style="">
        </div>
        <br> </br>
        <button type="submit" class="btn btn-lg btn-default btn-block">Verify</button>
      </form>
    </div> 
  </div>   
</div>

<#include "footer.ftl">