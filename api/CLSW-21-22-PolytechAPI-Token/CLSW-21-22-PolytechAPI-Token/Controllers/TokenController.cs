using CLSW_21_22_PolytechAPI_Token.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;

namespace CLSW_21_22_PolytechAPI_Token.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class TokenController : Controller
    {
        private readonly dbPAuthContext _context;

        public TokenController(dbPAuthContext context)
        {
            _context = context;
        }

        [HttpGet]
        public async Task<IActionResult> Index()
        {
            return View(await _context.TbToken.OrderByDescending(m=>m.DateRequest).ToListAsync());
        }

        [HttpGet("Generate/{seed}/{latitude}/{longitude}")]
        public string Get(int seed, double latitude, double longitude)
        {
            //seed is the heart rate of the requester
            //latitude and longitude are the current coordinates of the requester

            //generate 8 digits and chars characters
            //first 4 is heart rate * random value from alphabet char and get 4 first values
            string first4 = (seed * (new Random().Next(0, 26) + 65) * DateTime.Now.Millisecond).ToString().Substring(0, 4);
            //last 4 is latitude * longitude * currentmilis 
            string last4 = (latitude * longitude * DateTime.Now.Millisecond).ToString().Substring(0, 4);

            _context.TbToken.Add(new TbToken(seed, latitude, longitude, first4 + last4));
            _context.SaveChanges();

            return first4 + last4;
        }
    }
}
