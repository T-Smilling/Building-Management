<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Đăng kí tài khoản</title>
</head>
<body>
	<div class="container">
		<!-- <h1 class="form-heading">login Form</h1> -->
		<div class="login-form">
			<div class="main-div">
				<div class="container-fluid" >
					<section class="gradient-custom">
						<div class="page-wrapper">
							<div class="row d-flex justify-content-center align-items-center">
								<div class="col-12 col-md-8 col-lg-6 col-xl-5">
									<div class="card text-white" style="border-radius: 1rem; background-color: #35bf76;">
										<div class="card-body p-5">
											<div class="mb-md-5 mt-md-4 pb-5 text-center">
												<h2 class="fw-bold mb-2 text-uppercase">Register</h2>
												<p class="text-white-50 mb-5">Please enter your login, email and password!</p>
												<form>
												<div class="form-outline form-white mb-4">
													<label class="form-label" for="userName">Tên đăng nhập</label>
													<input type="text" class="form-control" id="userName" name="userName" placeholder="Tên đăng nhập" required>
												</div>

                                                <div class="form-outline form-white mb-4">
                                                    <label class="form-label" for="email">Email</label>
                                                    <input type="text" class="form-control" id="email" name="email" placeholder="Email" required>
                                                </div>

												<div class="form-outline form-white mb-4">
													<label class="form-label" for="password">Password</label>
													<input type="password" class="form-control" id="password" name="password" placeholder="Mật khẩu" required>
												</div>

												<div class="form-check d-flex justify-content-center mb-5">
													<div><input class="form-check-input me-2" type="checkbox" value="" id="form2Example3cg" /></div>
													<div><label class="form-check-label">
                                                        Remember Password
                                                    </label></div>
												</div>

												<button type="submit" class="btn btn-primary" id="addAccount">Đăng kí</button>
												</form>
												<div class="d-flex justify-content-center text-center mt-2 pt-1">
													<a href="#!" class="login-extension text-white"><i class="fab fa-facebook-f fa-lg"></i></a>
													<a href="#!" class="login-extension text-white"><i class="fab fa-twitter fa-lg mx-4 px-2"></i></a>
													<a href="#!" class="login-extension text-white"><i class="fab fa-google fa-lg"></i></a>
												</div>
											</div>
											<div class="text-center">
												<p class="mb-0 tex-center account">Did have an account? <a href="/login" class="text-white-50 fw-bold">Sign In</a></p>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div>
					</section>
				</div>
				<%--<script src="./assets/dist/js/boostrap-v5/bootstrap.js"></script>--%>
				<%--<script src="./assets/dist/js/fontawsome-v5/all.js"></script>--%>
			</div>
		</div>
	</div>
	<script>
        $('#addAccount').click(function (e) {
            e.preventDefault();
            var data = {};
            data['userName'] = $('input[name="userName"]').val();
            data['email'] = $('input[name="email"]').val();
            data['password'] = $('input[name="password"]').val();
            addInfo(data);
        });

        function addInfo(formData)
        {
            $.ajax({
                url: "/api/user",
                type: "POST",
                data: JSON.stringify(formData),
                contentType: "application/json",
                dataType: "JSON",
                success: function (response) {
                    alert("Đăng kí thành công");
                    window.location.href ="/login";
                },
                error: function (error) {
                    console.log(error);
                    alert("Đăng kí thất bại");
                    location.reload();
                }

            });
        }
	</script>
</body>
</html>