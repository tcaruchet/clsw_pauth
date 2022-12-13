using System;
using System.Collections.Generic;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace CLSW_21_22_PolytechAPI_Token.Models
{
    public partial class TbToken
    {
        public Guid Uid { get; set; }
        public DateTime DateRequest { get; set; }
        public int Bpm { get; set; }
        public double Lat { get; set; }
        public double Long { get; set; }
        public string Token { get; set; }

        public TbToken()
        {
        }

        public TbToken(int bpm, double lat, double @long, string token)
        {
            Bpm = bpm;
            Lat = lat;
            Long = @long;
            Token = token;
        }
    }
}
