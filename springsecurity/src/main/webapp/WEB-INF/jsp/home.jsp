<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="include/head.jsp" %>
		
		<!-- Dialog plugin -->
		<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.7/css/bootstrap-dialog.min.css" rel="stylesheet" type="text/css" />
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.7/js/bootstrap-dialog.min.js"></script>
		
		<script type="text/javascript">
		$(document).ready(function() {	
			
			$('#call_user').click( function(){ call("<c:url value='/user'/>"); });
			$('#call_admin').click( function(){ call("<c:url value='/admin'/>"); });
			
			function call(url){
				$.ajax({
					type: "GET",
					url: url,
					success: function (data, textStatus, xhr) {
						BootstrapDialog.show({
			                type: BootstrapDialog.TYPE_PRIMARY,
			                title: "Success",
			                message: data
			            });
					},
					error: function (xhr, textStatus, errorThrown) {
						BootstrapDialog.show({
			                type: BootstrapDialog.TYPE_WARNING,
			                title: "Error",
			                message: xhr.status + " " + errorThrown
			            });
					}
				});
			}
			
		});
		</script>
		
	</head>
	<body>
		
		<%@ include file="include/navbar.jsp" %>
		
		<div class="container">
			
			<h1>Welcome !</h1>
		
			<div class="container-fluid">
				<p>Thalassius vero ea tempestate praefectus praetorio praesens ipse quoque adrogantis ingenii, considerans incitationem eius ad multorum augeri discrimina, non maturitate vel consiliis mitigabat, ut aliquotiens celsae potestates iras principum molliverunt, sed adversando iurgandoque cum parum congrueret, eum ad rabiem potius evibrabat, Augustum actus eius exaggerando creberrime docens, idque, incertum qua mente, ne lateret adfectans. quibus mox Caesar acrius efferatus, velut contumaciae quoddam vexillum altius erigens, sine respectu salutis alienae vel suae ad vertenda opposita instar rapidi fluminis irrevocabili impetu ferebatur.</p>
				<p>Ut enim benefici liberalesque sumus, non ut exigamus gratiam (neque enim beneficium faeneramur sed natura propensi ad liberalitatem sumus), sic amicitiam non spe mercedis adducti sed quod omnis eius fructus in ipso amore inest, expetendam putamus.</p>
				<p>Quanta autem vis amicitiae sit, ex hoc intellegi maxime potest, quod ex infinita societate generis humani, quam conciliavit ipsa natura, ita contracta res est et adducta in angustum ut omnis caritas aut inter duos aut inter paucos iungeretur.</p>
				<p>Quam ob rem cave Catoni anteponas ne istum quidem ipsum, quem Apollo, ut ais, sapientissimum iudicavit; huius enim facta, illius dicta laudantur. De me autem, ut iam cum utroque vestrum loquar, sic habetote.</p>
				<p>Ardeo, mihi credite, Patres conscripti (id quod vosmet de me existimatis et facitis ipsi) incredibili quodam amore patriae, qui me amor et subvenire olim impendentibus periculis maximis cum dimicatione capitis, et rursum, cum omnia tela undique esse intenta in patriam viderem, subire coegit atque excipere unum pro universis. Hic me meus in rem publicam animus pristinus ac perennis cum C. Caesare reducit, reconciliat, restituit in gratiam.</p>
				
				<sec:authorize access="hasRole('ADMIN')">
					<div class="alert alert-info">
						<strong>Admin Note</strong> : Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae.</p>
					</div>
				</sec:authorize>
				
				<hr />
				
				<div class="row">
					<div class="col-md-6">
						<button id="call_user" class="btn btn-success btn-block" style="margin-top:20px">
							<span class="glyphicon glyphicon-user"></span> Test : Call REST Secured for USER
						</button>
					</div>
					<div class="col-md-6">
						<button id="call_admin" class="btn btn-success btn-block" style="margin-top:20px">
							<span class="glyphicon glyphicon-tower"></span> Test : Call REST Secured for ADMIN
						</button>
					</div>
				</div>
				
				<hr />
				
			</div>
			
		</div>
	</body>
</html>